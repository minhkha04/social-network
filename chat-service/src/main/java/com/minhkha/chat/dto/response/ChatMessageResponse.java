package com.minhkha.chat.dto.response;

import com.minhkha.chat.entity.ParticipantInfo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ChatMessageResponse {
    String id;
    String message;
    String conversationId;
    ParticipantInfo sender;
    Instant createdAt;
    boolean isMe;
}
