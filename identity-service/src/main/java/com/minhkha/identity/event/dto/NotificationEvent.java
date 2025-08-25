package com.minhkha.identity.event.dto;

import com.minhkha.identity.eums.Chanel;
import com.minhkha.identity.eums.TemplateCode;
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
