package com.minhkha.gateway.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class IntrospectRequest {

    @NotBlank(message = "Token không thể để trống")
    String token;
}
