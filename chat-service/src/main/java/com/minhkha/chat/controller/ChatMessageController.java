package com.minhkha.chat.controller;

import com.minhkha.chat.dto.request.ChatMessageRequest;
import com.minhkha.chat.dto.response.ApiResponse;
import com.minhkha.chat.dto.response.ChatMessageResponse;
import com.minhkha.chat.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/message")
public class ChatMessageController {

    ChatMessageService chatMessageService;

    @PostMapping("/create")
    public ApiResponse<ChatMessageResponse> createMessage(@RequestBody @Valid ChatMessageRequest request) {
        return ApiResponse.<ChatMessageResponse>builder()
                .data(chatMessageService.createChatMessage(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ChatMessageResponse>> getMessages(@RequestParam String conversationId) {
        return ApiResponse.<List<ChatMessageResponse>>builder()
                .data(chatMessageService.getMessages(conversationId))
                .build();
    }
}
