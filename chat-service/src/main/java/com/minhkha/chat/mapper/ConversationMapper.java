package com.minhkha.chat.mapper;

import com.minhkha.chat.dto.request.ConversationRequest;
import com.minhkha.chat.dto.response.ConversationResponse;
import com.minhkha.chat.entity.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    ConversationResponse toConversationResponse(Conversation request);

    Conversation toConversation(ConversationRequest request);
}
