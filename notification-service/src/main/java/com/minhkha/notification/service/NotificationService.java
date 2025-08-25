package com.minhkha.notification.service;

import com.minhkha.notification.event.dto.NotificationEvent;
import com.minhkha.notification.strategy.NotificationStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationService {

    NotificationStrategyFactory notificationStrategyFactory;

    public void processNotification(NotificationEvent event) {
        notificationStrategyFactory.getStrategy(event.getChannel()).sendNotification(event);
    }
}
