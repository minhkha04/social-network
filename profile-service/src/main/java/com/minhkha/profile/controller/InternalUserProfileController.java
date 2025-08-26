package com.minhkha.profile.controller;

import com.minhkha.profile.dto.request.UserProfileCreateRequest;
import com.minhkha.profile.dto.response.ApiResponse;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalUserProfileController {

    UserProfileService userService;

    @PostMapping()
    public UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request) {
            return userService.createUserProfile(request);
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<UserProfileResponse> getProfile(
            @PathVariable String userId
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.getProfileById(userId))
                .build();
    }
}
