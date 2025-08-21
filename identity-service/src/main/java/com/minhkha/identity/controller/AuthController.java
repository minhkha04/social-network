package com.minhkha.identity.controller;

import com.minhkha.identity.dto.request.*;
import com.minhkha.identity.dto.response.ApiResponse;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.dto.response.IntrospectResponse;
import com.minhkha.identity.eums.AuthProvider;
import com.minhkha.identity.eums.MailType;
import com.minhkha.identity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(
            @RequestParam AuthProvider provider,
            @RequestBody AuthRequest request
            ) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.login(request, provider))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(
            @RequestBody @Valid UserCreateRequest request
    ) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(
            @RequestBody @Valid IntrospectRequest request
    ) {
        return ApiResponse.<IntrospectResponse>builder()
                .data(authService.introspect(request))
                .build();
    }

    @PostMapping("/send-otp")
    public ApiResponse<Void> sendOtp(
            @RequestBody @Valid SendOtpRequest request,
            @RequestParam MailType type
    ) {
        authService.sendOtp(request, type);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<AuthenticationResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.resetPassword(request))
                .build();
    }

}
