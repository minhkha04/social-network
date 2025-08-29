package com.minhkha.chat.service;

import com.minhkha.chat.dto.request.IntrospectRequest;
import com.minhkha.chat.dto.response.IntrospectResponse;
import com.minhkha.chat.repository.httpclient.IdentityClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class IdentityService {

    IdentityClient identityClient;

    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            return identityClient.introspect(request).getData();
        } catch (FeignException ex) {
            log.error("Cannot introspect token", ex);
            return IntrospectResponse.builder()
                    .isValid(false)
                    .build();
        }
    }
}
