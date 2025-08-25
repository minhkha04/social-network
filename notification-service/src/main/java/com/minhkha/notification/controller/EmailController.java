package com.minhkha.notification.controller;

import com.minhkha.notification.strategy.EmailNotificationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailController {

    EmailNotificationStrategy emailNotificationStrategy;

//    @PostMapping("/email/send")
//    public ApiResponse<EmailResponse> sendEmail(@RequestBody EmailRequest request) {
//        return ApiResponse.<EmailResponse>builder()
//                .data(emailNotificationStrategy.sendMail(request))
//                .build();
//    }
}
