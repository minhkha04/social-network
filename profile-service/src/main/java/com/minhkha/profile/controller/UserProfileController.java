package com.minhkha.profile.controller;

import com.minhkha.profile.dto.request.UserProfileUpdateRequest;
import com.minhkha.profile.dto.response.ApiResponse;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/user-profiles")
public class UserProfileController {

    UserProfileService userService;

    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteUserProfileByUserId(@PathVariable String userId) {
        userService.deleteProfileByUserId(userId);
        return ApiResponse.builder().build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserProfileResponse> updateUserProfile(@RequestBody UserProfileUpdateRequest request, @PathVariable String userId) {
        return  ApiResponse.<UserProfileResponse>builder()
                .data(userService.updateProfile(request, userId))
                .build();
    }
}
