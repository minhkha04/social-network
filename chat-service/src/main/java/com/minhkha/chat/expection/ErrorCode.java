package com.minhkha.chat.expection;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(5001, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_ARGUMENT(5002, "Lỗi validation", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(5003, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    PARTICIPANT_NOT_FOUND(5004, "Không tìm thấy id partner", HttpStatus.NOT_FOUND),
    DUPLICATE_REQUEST(5005, "Yêu cầu bị trùng", HttpStatus.CONFLICT)
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
