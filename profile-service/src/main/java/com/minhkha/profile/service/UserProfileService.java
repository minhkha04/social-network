package com.minhkha.profile.service;

import com.minhkha.profile.dto.request.UserProfileCreateRequest;
import com.minhkha.profile.dto.request.UserProfileUpdateRequest;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.entity.UserProfile;
import com.minhkha.profile.expection.AppException;
import com.minhkha.profile.expection.ErrorCode;
import com.minhkha.profile.mapper.UserProfileMapper;
import com.minhkha.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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

    public UserProfileResponse getProfileByUserId(String userId) {
        return userProfileMapper.toUserProfileResponse(userProfileRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_PROFILE_NOT_FOUND)));
    }

    public void deleteProfileByUserId(String userId) {
        userProfileRepository.deleteByUserId(userId);
    }

    public UserProfileResponse updateProfile(UserProfileUpdateRequest request, String userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_PROFILE_NOT_FOUND));
        userProfileMapper.updateUserProfile(userProfile, request);
        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

}
