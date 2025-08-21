package com.minhkha.profile.service;

import com.minhkha.profile.dto.request.UserProfileCreateRequest;
import com.minhkha.profile.dto.request.UserProfileUpdateRequest;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.entity.UserProfile;
import com.minhkha.profile.mapper.UserProfileMapper;
import com.minhkha.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createUserProfile(UserProfileCreateRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getProfileById(String id) {
        return userProfileMapper.toUserProfileResponse(userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public void deleteProfileById(String id) {
        userProfileRepository.deleteById(id);
    }

    public UserProfileResponse updateProfile(UserProfileUpdateRequest request, String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userProfileMapper.updateUserProfile(userProfile, request);
        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

}
