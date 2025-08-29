package com.minhkha.chat.dto.response;

import com.minhkha.chat.entity.ParticipantInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ConversationResponse {
    String id;
    String type;
    String participantsHash;
    List<ParticipantInfo> participants;
    Instant createdAt;
    Instant modifiedAt;
    String conversationName;
    String conversationAvatarUrl;
}
