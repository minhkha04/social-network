package com.minhkha.identity.config;

import com.minhkha.identity.entity.User;
import com.minhkha.identity.eums.Role;
import com.minhkha.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsUserByRole(Role.ADMIN)) {
                User user = User.builder()
                        .email("admin@admin")
                        .password(passwordEncoder.encode("12345678"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(user);
                log.warn("Admin user has been created!");
            }
        };
    }
}
