package com.minhkha.post.expection;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(4001, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_ARGUMENT(4002, "Lỗi validation", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(4003, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED)
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
