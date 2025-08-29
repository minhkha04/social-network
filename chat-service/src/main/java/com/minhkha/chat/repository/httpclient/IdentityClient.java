package com.minhkha.chat.repository.httpclient;

import com.minhkha.chat.dto.request.IntrospectRequest;
import com.minhkha.chat.dto.response.ApiResponse;
import com.minhkha.chat.dto.response.IntrospectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "identity-service", url = "${app.identity.url}")
public interface IdentityClient {

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request);
}
