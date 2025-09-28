package com.github.aaric.saac.ws;

import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TomcatWebSocketHandler
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
//@Component
public class TomcatWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        String userId = HttpUtil.decodeParamMap(session.getUri().getQuery(), StandardCharsets.UTF_8).get("userId");
        SESSIONS.put(sessionId, session);
        System.err.println("Session(" + sessionId + "-" + userId + ") is opened.");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        SESSIONS.remove(sessionId);
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
        String chatId = UriComponentsBuilder.fromUri(Objects.requireNonNull(session.getUri()))
                .build().getQueryParams().getFirst("chatId");
        String playload = message.getPayload();
        System.err.println("Session(" + sessionId + "-" + chatId + ") is received: " + playload);
        session.sendMessage(new TextMessage("ECHO -> " + playload));
    }

    public void broadcastMessage(String message) {
        SESSIONS.forEach((sessionId, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (Exception e) {
                System.err.println("Session(" + sessionId + ") is error.");
            }
        });
    }

    @SneakyThrows(IOException.class)
    public void sendMessage(String sessionId, String message) {
        WebSocketSession session = SESSIONS.get(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
