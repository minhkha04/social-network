package com.minhkha.chat.mapper;

import com.minhkha.chat.dto.request.ChatMessageRequest;
import com.minhkha.chat.dto.response.ChatMessageResponse;
import com.minhkha.chat.entity.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

    ChatMessage toChatMessage(ChatMessageRequest request);
}
