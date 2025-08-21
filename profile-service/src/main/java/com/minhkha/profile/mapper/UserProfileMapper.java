package com.minhkha.profile.mapper;

import com.minhkha.profile.dto.request.UserProfileCreateRequest;
import com.minhkha.profile.dto.request.UserProfileUpdateRequest;
import com.minhkha.profile.dto.response.UserProfileResponse;
import com.minhkha.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile toUserProfile(UserProfileCreateRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    void updateUserProfile(@MappingTarget UserProfile userProfile, UserProfileUpdateRequest request);
}
