package com.i2s.worfklow_api_final.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2s.worfklow_api_final.dto.NotificationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private final Map<Long, WebSocketSession> sessions = new HashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Extract user id from session URI and add to sessions map
        Long userId = getUserIdFromSession(session);
        sessions.put(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle incoming message if necessary
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Remove session from sessions map
        Long userId = getUserIdFromSession(session);
        sessions.remove(userId);
    }

    public void sendNotificationToUser(Long userId, NotificationDTO notification) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(notification)));
            } catch (IOException e) {
                // Handle error when sending message
                e.printStackTrace();
            }
        }
    }

    private Long getUserIdFromSession(WebSocketSession session) {
        // Parse user id from session URI
        return new Long(1);
    }
}
