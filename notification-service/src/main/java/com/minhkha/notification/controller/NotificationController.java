package com.minhkha.notification.controller;

import com.minhkha.notification.event.dto.NotificationEvent;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    @KafkaListener(topics = "notification-delivery")
    public void listen(NotificationEvent message) {
        log.info("Message received: {}", message);

    }
}
