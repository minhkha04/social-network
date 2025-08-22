package com.minhkha.identity.strategy;

import com.minhkha.identity.dto.request.AuthRequest;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.AuthProvider;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.config.JwtProvider;
import com.minhkha.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailPasswordAuthStrategy implements AuthStrategy {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;

    @Override
    public AuthenticationResponse login(AuthRequest request) {
        if (request.getPassword().isBlank() || request.getEmail().isBlank()) {
            throw new AppException(ErrorCode.PASSWORD_EMAIL_NOT_BLANK);
        }
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        return AuthenticationResponse.builder()
                .token(jwtProvider.generateToken(user))
                .build();
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.EMAIL;
    }
}
