package com.minhkha.identity.service;

import com.minhkha.identity.dto.request.*;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.dto.response.IntrospectResponse;
import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.*;
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
    MailService mailService;
    MailOtpService mailOtpService;
    UserProfileClient userProfileClient;
    UserProfileMapper userProfileMapper;
    KafkaTemplate<String, String> kafkaTemplate;

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

        kafkaTemplate.send("user-created", "Welcome " + request.getFullName());
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

    public void sendOtp(SendOtpRequest request, MailType type) {
        switch (type) {
            case REGISTER_ACCOUNT -> {
                String otp = OtpUtil.generateOtp();
                mailOtpService.create(request.getEmail(), otp);
                MailRequest mailRequest = MailRequest.builder()
                        .mailType(MailType.REGISTER_ACCOUNT)
                        .toEmail(request.getEmail())
                        .subject("Verify your email")
                        .params(Map.of("otp", otp))
                        .build();
                mailService.sendMail(mailRequest);
            }
            case RESET_PASSWORD -> {
                userRepository.findUserByEmail(request.getEmail())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                String otp = OtpUtil.generateOtp();
                mailOtpService.create(request.getEmail(), otp);
                MailRequest mailRequest = MailRequest.builder()
                        .mailType(MailType.RESET_PASSWORD)
                        .toEmail(request.getEmail())
                        .subject("Change your password")
                        .params(Map.of("otp", otp))
                        .build();
                mailService.sendMail(mailRequest);
            }
            default -> throw new AppException(ErrorCode.RESEND_OTP_TYPE_INVALID);
        }
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
