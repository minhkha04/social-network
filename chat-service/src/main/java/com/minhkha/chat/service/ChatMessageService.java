package com.minhkha.chat.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhkha.chat.dto.request.ChatMessageRequest;
import com.minhkha.chat.dto.response.ChatMessageResponse;
import com.minhkha.chat.dto.response.UserProfileResponse;
import com.minhkha.chat.entity.ChatMessage;
import com.minhkha.chat.entity.Conversation;
import com.minhkha.chat.entity.ParticipantInfo;
import com.minhkha.chat.entity.WebSocketSession;
import com.minhkha.chat.expection.AppException;
import com.minhkha.chat.expection.ErrorCode;
import com.minhkha.chat.mapper.ChatMessageMapper;
import com.minhkha.chat.repository.ChatMessageRepository;
import com.minhkha.chat.repository.ConversationRepository;
import com.minhkha.chat.repository.httpclient.ProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatMessageService {

    SocketIOServer socketIOServer;
    ChatMessageRepository chatMessageRepository;
    ChatMessageMapper chatMessageMapper;
    ConversationRepository conversationRepository;
    ProfileClient profileClient;
    WebSocketSessionService webSocketSessionService;
    ObjectMapper objectMapper;


    private ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ChatMessageResponse chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);
        chatMessageResponse.setMe(userId.equals(chatMessage.getSender().getUserId()));

        return chatMessageResponse;
    }

    public ChatMessageResponse createChatMessage(ChatMessageRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Conversation conversation = conversationRepository.findById(request.getConversationId()).orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        conversation.getParticipants().stream()
                .filter(participantInfo -> participantInfo.getUserId().equals(userId))
                .findAny().orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));


        UserProfileResponse userProfileResponse = profileClient.getProfile(userId).getData();
        if (userProfileResponse == null) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        ParticipantInfo participantInfo = ParticipantInfo.builder()
                .fullName(userProfileResponse.getFullName())
                .userId(userProfileResponse.getUserId())
                .avatarUrl(userProfileResponse.getAvatarUrl())
                .build();

        ChatMessage chatMessage = chatMessageMapper.toChatMessage(request);
        chatMessage.setSender(participantInfo);
        chatMessage.setCreatedAt(Instant.now());

        chatMessage = chatMessageRepository.save(chatMessage);


        List<String> userIds = conversation.getParticipants()
                .stream()
                .map(ParticipantInfo::getUserId).toList();

        ChatMessageResponse chatMessageResponse = toChatMessageResponse(chatMessage);

        Map<String, WebSocketSession> socketSessionIds = webSocketSessionService.findByUserIdIn(userIds)
                .stream()
                .collect(Collectors.toMap(
                        WebSocketSession::getSocketSessionId,
                        Function.identity()));

        socketIOServer.getAllClients()
                .forEach(x -> {
                    WebSocketSession webSocketSession = socketSessionIds.get(x.getSessionId().toString());
                    if (Objects.nonNull(webSocketSession)) {
                        try {
                            chatMessageResponse.setMe(webSocketSession.getUserId().equals(userId));
                            x.sendEvent("message", objectMapper.writeValueAsString(chatMessageResponse));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        return chatMessageResponse;
    }


    public List<ChatMessageResponse> getMessages(String conversationId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        conversation.getParticipants().stream()
                .filter(participantInfo -> participantInfo.getUserId().equals(userId))
                .findAny().orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByConversationIdOrderByCreatedAtAsc(conversationId);


        return chatMessages.stream()
                .map(this::toChatMessageResponse)
                .toList();
    }
}
