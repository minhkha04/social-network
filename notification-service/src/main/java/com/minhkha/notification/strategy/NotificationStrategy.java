package com.minhkha.notification.strategy;

import com.minhkha.notification.enums.Chanel;
import com.minhkha.notification.event.dto.NotificationEvent;

public interface NotificationStrategy {
    Chanel getChanel();
    void sendNotification(NotificationEvent event);
}
