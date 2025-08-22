package com.minhkha.identity.mapper;

import com.minhkha.identity.dto.request.UserCreateRequest;
import com.minhkha.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toUser(UserCreateRequest request);

}
