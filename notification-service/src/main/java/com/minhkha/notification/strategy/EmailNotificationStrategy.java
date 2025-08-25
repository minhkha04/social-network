package com.minhkha.notification.strategy;

import com.minhkha.notification.enums.Chanel;
import com.minhkha.notification.event.dto.NotificationEvent;
import com.minhkha.notification.expection.AppException;
import com.minhkha.notification.expection.ErrorCode;
import com.minhkha.notification.helper.TemplateRender;
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
public class EmailNotificationStrategy implements NotificationStrategy {

    @NonFinal
    @Value("${app.resend.api.key}")
    String API_KEY;


    TemplateRender templateRender;


    private String generateHtml(NotificationEvent event) {
        String html;
        switch (event.getTemplateCode()) {
            case REGISTER_ACCOUNT -> html = templateRender.render("verify-email.html", event.getParams());
            case RESET_PASSWORD -> html = templateRender.render("reset-password.html", event.getParams());
            default -> throw new AppException(ErrorCode.EMAIL_TYPE_INVALID);
        }

        return html;
    }

    @Override
    public Chanel getChanel() {
        return Chanel.EMAIL;
    }

    @Override
    public void sendNotification(NotificationEvent event) {
        Resend resend = new Resend(API_KEY);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("My App <no-reply@techleaf.pro>")
                .to(event.getRecipient())
                .subject(event.getSubject())
                .html(generateHtml(event))
                .build();

        try {
            resend.emails().send(params);
            log.info("Email sent successfully: {}", event.getRecipient());
        } catch (ResendException e) {
            log.info("Error sending email via Resend: {}", e.getMessage());
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
