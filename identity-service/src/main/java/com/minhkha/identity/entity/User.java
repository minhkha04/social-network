package com.minhkha.identity.entity;

import com.minhkha.identity.eums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String email;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(length = 50, nullable = false)
    String fullName;

    @Column(nullable = false)
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDate birthDate;
}
