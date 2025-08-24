package com.minhkha.notification.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    String channel;
    String recipient;
    String templateCode;
    Map<String, Object> params;
    String subject;
    String body;
}
