package com.minhkha.post.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostRequest {
    String content;
}
