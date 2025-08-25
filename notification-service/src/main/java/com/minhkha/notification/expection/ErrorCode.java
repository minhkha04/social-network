package com.minhkha.notification.expection;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(3001, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    INVALID_ARGUMENT(3002, "Lỗi validation", HttpStatus.BAD_REQUEST),
    TEMPLATE_NOT_FOUND(3003, "Không tìm thấy template mail", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_SEND_FAILED(3004, "Gửi mail thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_TYPE_INVALID(3005, "Mail type không tồn tại", HttpStatus.BAD_REQUEST),
    CHANEL_NOT_EXISTED(3006, "Chanel không tồn tại", HttpStatus.BAD_REQUEST),
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
