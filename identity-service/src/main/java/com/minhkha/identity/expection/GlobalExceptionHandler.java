package com.minhkha.identity.expection;

import com.minhkha.identity.dto.response.ApiResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        log.error("Exception: {}", exception.getMessage());
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.<Void>builder()
                        .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                        .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error("MissingServletRequestParameterException: {}", exception.getMessage());
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("AccessDeniedException: {}", exception.getMessage());
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.<Void>builder()
                        .code(ErrorCode.UNAUTHORIZED.getCode())
                        .message(ErrorCode.UNAUTHORIZED.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
        log.error("AppException: {}", exception.getMessage());
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(ErrorCode.INVALID_ARGUMENT.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.<Void>builder()
                        .code(ErrorCode.INVALID_ARGUMENT.getCode())
                        .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                        .build());
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<ApiResponse<Void>> handleFeignException(FeignException exception) {
        log.error("FeignException: {}", exception.getMessage());
        return ResponseEntity
                .status(ErrorCode.INVALID_ARGUMENT.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.<Void>builder()
                        .code(ErrorCode.INVALID_ARGUMENT.getCode())
                        .message("Oke")
                        .build());
    }
}
