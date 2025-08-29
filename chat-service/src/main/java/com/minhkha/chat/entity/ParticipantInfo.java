package com.minhkha.chat.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "participant_info")
public class ParticipantInfo {

    String userId;
    String fullName;
    String avatarUrl;
}
