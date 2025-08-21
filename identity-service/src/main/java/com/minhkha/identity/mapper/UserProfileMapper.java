package com.minhkha.identity.mapper;

import com.minhkha.identity.dto.request.UserProfileCreateRequest;
import com.minhkha.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "userId", source = "request.id")
    UserProfileCreateRequest toUserProfileCreateRequest(User request);
}
