package com.minhkha.profile.repository.httpClient;

import com.minhkha.profile.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "profile-service", url = "${app.service.url}" )
public interface UserProfileClient {

    @PostMapping(value = "/internal/upload/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<String> uploadImage(@RequestPart("file") MultipartFile file);

}
