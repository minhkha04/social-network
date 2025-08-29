package com.minhkha.chat.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.minhkha.chat.dto.request.IntrospectRequest;
import com.minhkha.chat.entity.WebSocketSession;
import com.minhkha.chat.service.IdentityService;
import com.minhkha.chat.service.WebSocketSessionService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SocketHandler {

    SocketIOServer socketIOServer;
    IdentityService identityService;
    WebSocketSessionService webSocketSessionService;

    @OnConnect
    public void clientConnected(SocketIOClient client) {

        String token = client.getHandshakeData().getSingleUrlParam("token");
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        log.info("UserId: {}", userId);
        boolean isValid =  identityService.introspect(IntrospectRequest.builder().token(token).build()).isValid();

        if (!isValid) {
            log.error("Invalid token: {}", token);
            client.disconnect();
            return;
        }
        WebSocketSession webSocketSession = WebSocketSession.builder()
                .socketSessionId(client.getSessionId().toString())
                .userId(userId)
                .createdAt(Instant.now())
                .build();

        webSocketSessionService.create(webSocketSession);
        log.info("Token: {}", token);
        log.info("Client connected: {}", client.getSessionId());
    }

    @OnDisconnect
    public void clientDisconnected(SocketIOClient client) {
        webSocketSessionService.delete(client.getSessionId().toString());
        log.info("Client disconnected: {}", client.getSessionId());
    }

    @PostConstruct
    public void init() {
        socketIOServer.start();
        socketIOServer.addListeners(this);
        log.info("SocketIO server started");
    }

    @PreDestroy
    public void destroy() {
        socketIOServer.stop();
        log.info("SocketIO server stopped");
    }

}
