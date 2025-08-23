package com.minhkha.identity.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserProfileCreateRequest {
    String userId;
    String fullName;
    LocalDate birthDate;
    String city;
    String sex;
    String avatarUrl;
    String email;
}
