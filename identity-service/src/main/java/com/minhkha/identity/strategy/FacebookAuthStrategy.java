package com.minhkha.identity.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhkha.identity.dto.request.AuthRequest;
import com.minhkha.identity.dto.request.UserProfileCreateRequest;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.AuthProvider;
import com.minhkha.identity.eums.Chanel;
import com.minhkha.identity.eums.Role;
import com.minhkha.identity.eums.TemplateCode;
import com.minhkha.identity.event.dto.NotificationEvent;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.config.JwtProvider;
import com.minhkha.identity.mapper.UserProfileMapper;
import com.minhkha.identity.repository.UserRepository;
import com.minhkha.identity.repository.httpclient.UserProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FacebookAuthStrategy implements AuthStrategy {

    UserRepository userRepository;
    JwtProvider jwtProvider;
    UserProfileClient userProfileClient;
    UserProfileMapper userProfileMapper;
    KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    private JsonNode verifyToken(String accessToken) {
        try {
            // Gọi Graph API để lấy thông tin user
            String url = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accessToken;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new AppException(ErrorCode.INVALID_FACEBOOK_TOKEN);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.body());

        } catch (IOException | InterruptedException e) {
            throw new AppException(ErrorCode.INVALID_FACEBOOK_TOKEN);
        }
    }

    @Override
    public AuthenticationResponse login(AuthRequest request) {
        if (request.getAccessToken().isBlank()) {
            throw new AppException(ErrorCode.ACCESS_TOKEN_REQUIRED);
        }
        JsonNode fbUser = verifyToken(request.getAccessToken());
        String email = fbUser.has("email") ? fbUser.get("email").asText() : fbUser.get("id").asText() + "@facebook.com";
        String name = fbUser.has("name") ? fbUser.get("name").asText() : "Facebook User";
        String picture = fbUser.path("picture").path("data").path("url").asText();
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                    .email(email)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            UserProfileCreateRequest userProfileCreateRequest = userProfileMapper.toUserProfileCreateRequest(user);
            userProfileCreateRequest.setFullName(name);
            userProfileCreateRequest.setAvatarUrl(picture);
            userProfileClient.createUserProfile(userProfileCreateRequest);

            NotificationEvent notificationEvent = NotificationEvent.builder()
                    .recipient(email)
                    .subject("Welcome to Book Social Network")
                    .params(Map.of("userName", name))
                    .channel(Chanel.EMAIL)
                    .templateCode(TemplateCode.WELCOME_NEW_USER)
                    .build();
            kafkaTemplate.send("notification-delivery", notificationEvent);
        }
        String token = jwtProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).build();
    }


    @Override
    public AuthProvider getProvider() {
        return AuthProvider.FACEBOOK;
    }
}