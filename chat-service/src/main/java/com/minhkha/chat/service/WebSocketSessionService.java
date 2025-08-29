package com.minhkha.chat.service;

import com.minhkha.chat.entity.WebSocketSession;
import com.minhkha.chat.repository.WebSocketSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketSessionService {

    WebSocketSessionRepository webSocketSessionRepository;

    public WebSocketSession create(WebSocketSession webSocketSession) {
        return webSocketSessionRepository.save(webSocketSession);
    }

    public void delete(String socketSessionId) {
        webSocketSessionRepository.deleteBySocketSessionId(socketSessionId);
    }

    public List<WebSocketSession> findByUserIdIn(List<String> userIds) {
        return webSocketSessionRepository.findAllByUserIdIn(userIds);
    }
}
