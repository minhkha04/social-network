package com.minhkha.identity.service;

import com.minhkha.identity.dto.request.MailRequest;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.helper.TemplateRender;
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
public class MailService {

    @NonFinal
    @Value("${resend.api.key}")
    String API_KEY;

    @NonFinal
    @Value("${resend.from}")
    String FROM_EMAIL;

    TemplateRender templateRender;


    private String generateHtml(MailRequest request) {
        String html;
        switch (request.getMailType()) {
            case REGISTER_ACCOUNT -> html = templateRender.render("verify-email.html", request.getParams());
            case RESET_PASSWORD -> html = templateRender.render("reset-password.html", request.getParams());
            default -> throw new AppException(ErrorCode.EMAIL_TYPE_INVALID);
        }

        return html;
    }

    public void sendMail(MailRequest request) {
        Resend resend = new Resend(API_KEY);

         CreateEmailOptions params = CreateEmailOptions.builder()
                .from(FROM_EMAIL)
                .to(request.getToEmail())
                .subject(request.getSubject())
                .html(generateHtml(request))
                .build();

        try {
            resend.emails().send(params);
        } catch (ResendException e) {
            log.info("Error sending email via Resend: {}", e.getMessage());
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

}
