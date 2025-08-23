package com.minhkha.identity.repository.httpclient;

import com.minhkha.identity.dto.request.UserProfileCreateRequest;
import com.minhkha.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.service.url}" )
public interface UserProfileClient {

    @PostMapping(value = "/internal", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request);

}
