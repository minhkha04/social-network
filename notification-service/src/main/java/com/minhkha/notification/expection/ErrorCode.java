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
    EMAIL_TYPE_INVALID(1011, "Mail type không tồn tại", HttpStatus.BAD_REQUEST),
    RESEND_OTP_TYPE_INVALID(1012, "Resend otp type không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(1013, "Email không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    OTP_INVALID(1014, "Mã OTP không đúng", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1015, "Mã OTP đã hết hạn", HttpStatus.BAD_REQUEST),
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
