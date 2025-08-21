package com.minhkha.identity.strategy;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.minhkha.identity.dto.request.AuthRequest;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.AuthProvider;
import com.minhkha.identity.eums.Role;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.expection.JwtProvider;
import com.minhkha.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoogleAuthStrategy implements AuthStrategy {

    UserRepository userRepository;
    JwtProvider jwtProvider;

    @NonFinal
    @Value("${google_client_id}")
    String GOOGLE_CLIENT_ID;

    private Payload verifyToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .build();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new AppException(ErrorCode.INVALID_GOOGLE_TOKEN);
            }
            return idToken.getPayload();
        } catch (GeneralSecurityException | IOException e) {
            throw new AppException(ErrorCode.INVALID_GOOGLE_TOKEN);
        }
    }

    @Override
    public AuthenticationResponse login(AuthRequest request) {
        if (request.getAccessToken().isBlank()) {
            throw new AppException(ErrorCode.ACCESS_TOKEN_REQUIRED);
        }
        Payload payload = verifyToken(request.getAccessToken());
        String name = (String) payload.get("name");
        String email = payload.getEmail();
        String picture = (String) payload.get("picture");
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                    .email(email)
                    .fullName(name)
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
        String token = jwtProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).build();
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.GOOGLE;
    }
}
