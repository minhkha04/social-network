package com.minhkha.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatMessageRequest {
    @NotBlank
    String conversationId;
    @NotBlank
    String message;
}
