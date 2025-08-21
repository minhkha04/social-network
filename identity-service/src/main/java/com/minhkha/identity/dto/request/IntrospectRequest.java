package com.minhkha.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IntrospectRequest {

    @NotBlank(message = "Token không thể để trống")
    String token;
}
