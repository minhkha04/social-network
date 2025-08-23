package com.minhkha.profile.expection;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(2001, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_ARGUMENT(2002, "Lỗi validation", HttpStatus.BAD_REQUEST),
    USER_PROFILE_NOT_FOUND(2003, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(2004, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED)
    ;
    int code;
    String message;
    HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
