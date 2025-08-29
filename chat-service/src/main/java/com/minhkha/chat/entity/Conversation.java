package com.minhkha.chat.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

@Document(collection = "conversation")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Conversation {

    @MongoId
    String id;

    String type;

    @Indexed(unique = true)
    String participantsHash;

    List<ParticipantInfo> participants;

    Instant createdAt;

    Instant modifiedAt;

}
