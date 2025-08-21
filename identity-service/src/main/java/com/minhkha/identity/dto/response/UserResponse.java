package com.minhkha.identity.dto.response;

import com.minhkha.identity.eums.Role;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    String id;
    String email;
    String fullName;
    Role role;
    Date birthDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
