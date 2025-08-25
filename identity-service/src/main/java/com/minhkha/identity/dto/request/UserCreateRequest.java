package com.minhkha.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minhkha.identity.validation.BirthDate;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserCreateRequest {

    @Email(message = "Email không đúng định dạng")
    @NotBlank(message = "Email không được để trống")
    String email;

    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6 đến 255 ký tự")
    @NotBlank(message = "Mật khẩu không được để trống")
    String password;

    @Pattern(
            regexp = "^[A-Za-zÀ-Ỹà-ỹ\\s]+$",
            message = "Họ tên không được chứa ký tự đặc biệt hoặc số"
    )
    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 50, message = "Họ tên phải từ 2 đến 50 ký tự")
    String fullName;

    @NotNull(message = "Ngày sinh không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @BirthDate(min = 21, message = "Bạn phải đủ 21 tuổi")
    LocalDate birthDate;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 6, min = 6, message = "Otp phải có 6 ký tự")
    @Pattern(regexp = "^[0-9]{6}$", message = "Mã OTP chỉ được chứa số")
    String otp;
}
