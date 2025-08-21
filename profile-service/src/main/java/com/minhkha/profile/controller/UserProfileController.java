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
@RequestMapping("/v1/user-profiles")
public class UserProfileController {

    UserProfileService userService;

    @PostMapping()
    public UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request) {
            return userService.createUserProfile(request);
    }

    @GetMapping("/{id}")
    public UserProfileResponse getUserProfileById(@PathVariable String id) {
        return userService.getProfileById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserProfileById(@PathVariable String id) {
        userService.deleteProfileById(id);
    }


    @PutMapping("/{id}")
    public UserProfileResponse updateUserProfile(@RequestBody UserProfileUpdateRequest request, @PathVariable String id) {
        return  userService.updateProfile(request, id);
    }
}
