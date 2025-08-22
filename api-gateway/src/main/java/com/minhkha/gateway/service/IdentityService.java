package com.minhkha.gateway.service;

import com.minhkha.gateway.dto.request.IntrospectRequest;
import com.minhkha.gateway.dto.response.ApiResponse;
import com.minhkha.gateway.dto.response.IntrospectResponse;
import com.minhkha.gateway.repository.IdentityClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class IdentityService {

    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {

        return identityClient.introspect(
                IntrospectRequest.builder()
                        .token(token)
                        .build()
        );
    }
}
