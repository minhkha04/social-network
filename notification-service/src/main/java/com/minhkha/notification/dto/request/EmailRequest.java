package com.minhkha.notification.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmailRequest {
    String from;
    String to;
    String subject;
    String content;
}
