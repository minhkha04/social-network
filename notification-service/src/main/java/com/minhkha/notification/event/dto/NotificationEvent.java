package com.minhkha.notification.event.dto;

import com.minhkha.notification.enums.Chanel;
import com.minhkha.notification.enums.TemplateCode;
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
    Chanel channel;
    String recipient;
    TemplateCode templateCode;
    Map<String, Object> params;
    String subject;
}
