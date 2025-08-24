package com.minhkha.notification.service;

import com.minhkha.notification.dto.request.EmailRequest;
import com.minhkha.notification.dto.response.EmailResponse;
import com.minhkha.notification.expection.AppException;
import com.minhkha.notification.expection.ErrorCode;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @NonFinal
    @Value("${app.resend.api.key}")
    String API_KEY;

    public EmailResponse sendMail(EmailRequest request) {
        Resend resend = new Resend(API_KEY);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(request.getFrom())
                .to(request.getTo())
                .subject(request.getSubject())
                .html(request.getContent())
                .build();

        try {
            return EmailResponse.builder().id(resend.emails().send(params).getId()).build();
        } catch (ResendException e) {
            log.info("Error sending email via Resend: {}", e.getMessage());
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
