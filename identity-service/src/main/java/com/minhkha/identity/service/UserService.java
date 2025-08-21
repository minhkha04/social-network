package com.minhkha.identity.service;

import com.minhkha.identity.dto.response.UserResponse;
import com.minhkha.identity.entity.User;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.mapper.UserMapper;
import com.minhkha.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

     UserRepository userRepository;
     UserMapper userMapper;

     public UserResponse getMyInfo() {
         String userId = SecurityContextHolder.getContext().getAuthentication().getName();
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

         return userMapper.toUserResponse(user);

     }
}
