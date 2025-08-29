package com.minhkha.profile.controller;

import com.minhkha.profile.dto.request.UserProfileUpdateRequest;
import com.minhkha.profile.dto.response.ApiResponse;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserProfileController {

    UserProfileService userProfileService;

    @GetMapping()
    public ApiResponse<UserProfileResponse> getUserProfile() {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.getProfile())
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteUserProfileByUserId(@PathVariable String userId) {
        userProfileService.deleteProfileByUserId(userId);
        return ApiResponse.builder().build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserProfileResponse> updateUserProfile(@RequestBody UserProfileUpdateRequest request, @PathVariable String userId) {
        return  ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.updateProfile(request, userId))
                .build();
    }

    @PutMapping("/avatar")
    public ApiResponse<UserProfileResponse> updateAvatar(@RequestParam("file") MultipartFile file) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.updateAvatar(file))
                .build();
    }

    @GetMapping("/find-by-full-name")
    public ApiResponse<List<UserProfileResponse>> searchUserByFullName(@RequestParam String fullName) {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .data(userProfileService.searchUserByFullName(fullName))
                .build();
    }

}
