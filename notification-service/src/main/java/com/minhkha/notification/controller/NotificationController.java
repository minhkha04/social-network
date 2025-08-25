package com.minhkha.notification.controller;

import com.minhkha.notification.event.dto.NotificationEvent;
import com.minhkha.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationController {

    NotificationService notificationService;

    @KafkaListener(topics = "notification-delivery")
    public void listen(NotificationEvent event) {
        log.info("Message received: {}", event);
        notificationService.processNotification(event);
    }
}
