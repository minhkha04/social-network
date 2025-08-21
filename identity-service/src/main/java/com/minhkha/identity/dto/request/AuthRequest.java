package com.minhkha.identity.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class AuthRequest {
    String email;
    String password;
    String accessToken;
}
