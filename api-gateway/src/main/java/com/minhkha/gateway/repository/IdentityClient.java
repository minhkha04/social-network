package com.minhkha.gateway.repository;

import com.minhkha.gateway.dto.request.IntrospectRequest;
import com.minhkha.gateway.dto.response.ApiResponse;
import com.minhkha.gateway.dto.response.IntrospectResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Component
public interface IdentityClient {

    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody @Valid IntrospectRequest request);
}
