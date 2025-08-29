package com.minhkha.identity.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectResponse {
    boolean isValid;
}
