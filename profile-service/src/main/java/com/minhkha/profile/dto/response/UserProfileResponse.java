package com.minhkha.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String userId;
    String fullName;
    LocalDate birthDate;
    String city;
    String sex;
    String avatarUrl;
    String email;
}
