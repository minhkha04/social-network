package com.minhkha.chat.controller;

import com.minhkha.chat.dto.request.ConversationRequest;
import com.minhkha.chat.dto.response.ApiResponse;
import com.minhkha.chat.dto.response.ConversationResponse;
import com.minhkha.chat.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ConversationController {

    ConversationService conversationService;

    @PostMapping("/create")
    public ApiResponse<ConversationResponse> createConversation(@RequestBody @Valid ConversationRequest request) {
        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.createConversation(request))
                .build();
    }

    @GetMapping("/my-conversations")
    public ApiResponse<List<ConversationResponse>> getMyConversations() {
        return ApiResponse.<List<ConversationResponse>>builder()
                .data(conversationService.myConversation())
                .build();
    }
}
