package com.minhkha.chat.repository;

import com.minhkha.chat.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {

    @Query("{'participants.userId': ?0}")
    List<Conversation> findAllByParticipantIdContains(String userId);

    Optional<Conversation> findByParticipantsHash(String participantsHash);
}
