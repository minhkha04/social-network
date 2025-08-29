package com.minhkha.chat.repository;

import com.minhkha.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findAllByConversationIdOrderByCreatedAtAsc(String conversationId);
}
