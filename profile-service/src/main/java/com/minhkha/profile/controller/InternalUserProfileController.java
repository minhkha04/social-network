package com.minhkha.profile.controller;

import com.minhkha.profile.dto.request.UserProfileCreateRequest;
import com.minhkha.profile.dto.request.UserProfileUpdateRequest;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/internal/user-profiles")
public class InternalUserProfileController {

    UserProfileService userService;

    @PostMapping()
    public UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request) {
            return userService.createUserProfile(request);
    }

    @GetMapping("/{userId}")
    public UserProfileResponse getUserProfileByUserId(@PathVariable String userId) {
        return userService.getProfileByUserId(userId);
    }
}
