package com.minhkha.identity.expection;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1001, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1002, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    INVALID_AUTH_PROVIDER(1003, "Method login không tồn tại", HttpStatus.BAD_REQUEST)
    ,
    PASSWORD_EMAIL_NOT_BLANK(1004, "Email và Password không được để trống", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "Tài khoản không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MATCH(1006, "Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXIST(1007,"Email đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT(1008, "Lỗi validation", HttpStatus.BAD_REQUEST),
    TEMPLATE_NOT_FOUND(1010, "Không tìm thấy template mail", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_TYPE_INVALID(1011, "Mail type không tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1012, "Gửi mail thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    RESEND_OTP_TYPE_INVALID(1012, "Resend otp type không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(1013, "Email không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    OTP_INVALID(1014, "Mã OTP không đúng", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1015, "Mã OTP đã hết hạn", HttpStatus.BAD_REQUEST),
    INVALID_GOOGLE_TOKEN(1016, "Token Google không hợp lệ", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_REQUIRED(1017, "Access token là bắt buộc", HttpStatus.BAD_REQUEST),
    INVALID_FACEBOOK_TOKEN(1017, "Token Facebook không hợp lệ", HttpStatus.UNAUTHORIZED),
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
