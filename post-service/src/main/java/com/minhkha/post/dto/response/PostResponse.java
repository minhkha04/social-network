package com.minhkha.post.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostResponse {
    String id;
    String userId;
    String content;
    Instant createdAt;
    Instant modifiedAt;
    String created;
}
