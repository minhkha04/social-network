package com.minhkha.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantInfo extends MongoRepository<ParticipantInfo, String> {
}
