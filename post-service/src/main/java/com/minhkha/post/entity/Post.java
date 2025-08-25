package com.minhkha.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Document(collection = "post")
@Builder
public class Post {
    @MongoId
    String id;
    String userId;
    String content;
    Instant createdAt;
    Instant modifiedAt;
}
