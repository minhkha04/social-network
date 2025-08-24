package com.minhkha.notification.controller;

import com.minhkha.notification.dto.request.EmailRequest;
import com.minhkha.notification.dto.response.ApiResponse;
import com.minhkha.notification.dto.response.EmailResponse;
import com.minhkha.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailController {

    EmailService emailService;

    @PostMapping("/email/send")
    public ApiResponse<EmailResponse> sendEmail(@RequestBody EmailRequest request) {
        return ApiResponse.<EmailResponse>builder()
                .data(emailService.sendMail(request))
                .build();
    }
}
