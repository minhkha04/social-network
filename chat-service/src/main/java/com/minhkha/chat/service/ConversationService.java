package com.minhkha.chat.service;

import com.minhkha.chat.dto.request.ConversationRequest;
import com.minhkha.chat.dto.response.ConversationResponse;
import com.minhkha.chat.dto.response.UserProfileResponse;
import com.minhkha.chat.entity.Conversation;
import com.minhkha.chat.entity.ParticipantInfo;
import com.minhkha.chat.expection.AppException;
import com.minhkha.chat.expection.ErrorCode;
import com.minhkha.chat.mapper.ConversationMapper;
import com.minhkha.chat.repository.ConversationRepository;
import com.minhkha.chat.repository.httpclient.ProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConversationService {

    ConversationRepository conversationRepository;
    ProfileClient profileClient;
    ConversationMapper conversationMapper;

    private String generateParticipantsHash(List<String> userIds) {
        StringJoiner joiner = new StringJoiner("_");
        userIds.forEach(joiner::add);
        return joiner.toString();
    }

    private ConversationResponse toConversationResponse(Conversation conversation, String currentUserId) {
        ConversationResponse response = conversationMapper.toConversationResponse(conversation);
        conversation.getParticipants()
                .stream()
                .filter(participantInfo -> !participantInfo.getUserId().equals(currentUserId))
                .findFirst().ifPresent(participantInfo -> {
                    response.setConversationAvatarUrl(participantInfo.getAvatarUrl());
                    response.setConversationName(participantInfo.getFullName());
                });
        return response;
    }

    public ConversationResponse createConversation(ConversationRequest request) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        UserProfileResponse profileResponse = profileClient.getProfile(userId).getData();
        UserProfileResponse participantProfileResponse = profileClient.getProfile(request.getParticipantId().getFirst()).getData();

        if (participantProfileResponse == null || profileResponse == null) {
            throw new AppException(ErrorCode.PARTICIPANT_NOT_FOUND);
        }

        List<String> userIds = List.of(
                userId,
                request.getParticipantId().getFirst()
        );

        List<String> sortedId = userIds.stream().sorted().toList();
        String userIdsHash = generateParticipantsHash(sortedId);

        List<ParticipantInfo> participantInfos = List.of(
                ParticipantInfo.builder()
                        .userId(profileResponse.getUserId())
                        .fullName(profileResponse.getFullName())
                        .avatarUrl(profileResponse.getAvatarUrl())
                        .build(),
                ParticipantInfo.builder()
                        .userId(participantProfileResponse.getUserId())
                        .fullName(participantProfileResponse.getFullName())
                        .avatarUrl(participantProfileResponse.getAvatarUrl())
                        .build()
        );

        Conversation conversation = Conversation.builder()
                .type(request.getType())
                .participantsHash(userIdsHash)
                .createdAt(Instant.now())
                .modifiedAt(Instant.now())
                .participants(participantInfos)
                .build();
        try {
            conversation = conversationRepository.save(conversation);
        } catch (DuplicateKeyException e) {
            conversation = conversationRepository.findByParticipantsHash(userIdsHash)
                    .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        }
        return toConversationResponse(conversation, userId);
    }

    public List<ConversationResponse> myConversation() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Conversation> conversations = conversationRepository.findAllByParticipantIdContains(userId);
        return conversations.stream()
                .map(conversation -> toConversationResponse(conversation, userId))
                .toList();
    }

}
