package com.github.aaric.saac.ws;

import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TomcatWebSocketHandler
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Component
public class TomcatWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        String query = session.getUri().getQuery();
        String userId = HttpUtil.decodeParamMap(query, StandardCharsets.UTF_8).get("userId");
        sessions.put(sessionId, session);
        System.err.println("Session(" + sessionId + "-" + userId + ") is opened.");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.err.println("Session(" + sessionId + ") is closed.");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String sessionId = session.getId();
        System.err.println("Session(" + sessionId + ") is error.");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sessionId = session.getId();
        String playload = message.getPayload();
        System.err.println("Session(" + sessionId + ") is received: " + playload);
        session.sendMessage(new TextMessage("ECHO -> " + playload));
    }

    public void broadcastMessage(String message) {
        sessions.forEach((sessionId, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (Exception e) {
                System.err.println("Session(" + sessionId + ") is error.");
            }
        });
    }

    public void sendMessage(String sessionId, String message) throws IOException {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
