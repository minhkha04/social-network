package com.minhkha.identity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailOtp {
    @Id
    String email;

    @Column(nullable = false)
    String otp;

    @Column(nullable = false)
    LocalDateTime expiredTime;

}
