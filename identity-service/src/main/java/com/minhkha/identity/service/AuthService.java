package com.minhkha.identity.service;

import com.minhkha.identity.dto.request.*;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.dto.response.IntrospectResponse;
import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.*;
import com.minhkha.identity.event.dto.NotificationEvent;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.config.JwtProvider;
import com.minhkha.identity.mapper.UserMapper;
import com.minhkha.identity.mapper.UserProfileMapper;
import com.minhkha.identity.repository.UserRepository;
import com.minhkha.identity.repository.httpclient.UserProfileClient;
import com.minhkha.identity.strategy.AuthStrategyFactory;
import com.minhkha.identity.utils.OtpUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    AuthStrategyFactory authStrategyFactory;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;
    MailOtpService mailOtpService;
    UserProfileClient userProfileClient;
    UserProfileMapper userProfileMapper;
    KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public AuthenticationResponse login(AuthRequest request, AuthProvider authProvider) {
        return authStrategyFactory.getStrategy(authProvider).login(request);
    }

    @Transactional
    public AuthenticationResponse register(UserCreateRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user = userRepository.save(user);
        UserProfileCreateRequest userProfileCreateRequest = userProfileMapper.toUserProfileCreateRequest(user);
        userProfileCreateRequest.setFullName(request.getFullName());
        userProfileCreateRequest.setBirthDate(request.getBirthDate());
        userProfileClient.createUserProfile(userProfileCreateRequest);


//        mailOtpService.verify(request.getEmail(), request.getOtp());

//        NotificationEvent notificationEvent = NotificationEvent.builder()
//                .recipient(request.getEmail())
//                .subject("Welcome to Book Social Network")
//                .body("You have successfully registered an account")
//                .build();
//        kafkaTemplate.send("notification-delivery", notificationEvent);

        return AuthenticationResponse.builder()
                .token(jwtProvider.generateToken(user))
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        boolean isValid = true;
        try {
            jwtProvider.verifyToken(request.getToken());
        } catch (AppException ex) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }

    public void sendOtp(SendOtpRequest request, TemplateCode templateCode) {
        String otp = OtpUtil.generateOtp();
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        mailOtpService.create(request.getEmail(), otp, expiredTime);
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel(Chanel.EMAIL)
                .recipient(request.getEmail())
                .subject(switch (templateCode) {
                    case REGISTER_ACCOUNT -> "Verify your account";
                    case RESET_PASSWORD -> "Change your password";
                    default -> throw new AppException(ErrorCode.RESEND_OTP_TYPE_INVALID);
                })
                .templateCode(templateCode)
                .params(Map.of(
                        "otp", otp,
                        "expiredTime", 5
                ))
                .build();
        kafkaTemplate.send("notification-delivery", notificationEvent);
    }


    public AuthenticationResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//        mailOtpService.verify(request.getEmail(), request.getOtp());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtProvider.generateToken(user))
                .build();
    }
}
