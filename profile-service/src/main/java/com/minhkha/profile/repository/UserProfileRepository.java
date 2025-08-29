package com.minhkha.profile.repository;

import com.minhkha.profile.entity.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    Optional<UserProfile> findByUserId(String userId);

    void deleteByUserId(String userId);

    List<UserProfile> findByFullNameContainingIgnoreCase(String fullName);
}
