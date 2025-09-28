package com.github.aaric.saac.ws;

import jakarta.websocket.*;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UndertowWebSocketEndpoint
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Component
@ServerEndpoint(value = "/ws/chat/{roomId}", configurator = UndertowWebSocketEndpoint.QueryParamConfigurator.class)
public class UndertowWebSocketEndpoint {

    public static class QueryParamConfigurator extends ServerEndpointConfig.Configurator {

        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            Map<String, List<String>> queryParams = request.getParameterMap();
            List<String> userIds = queryParams.get("userId");

            if (userIds != null && !userIds.isEmpty()) {
                sec.getUserProperties().put("userId", userIds.get(0));
            }

            super.modifyHandshake(sec, request, response);
        }
    }

    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        String sessionId = session.getId();
//        String userId = HttpUtil.decodeParamMap(session.getQueryString(), StandardCharsets.UTF_8).get("userId");
        String userId = (String) session.getUserProperties().get("userId");
        SESSIONS.put(sessionId, session);
        System.err.println("Session(" + sessionId + "-" + roomId + "-" + userId + ") is opened.");
    }

    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
        SESSIONS.remove(sessionId);
        System.err.println("Session(" + sessionId + ") is closed.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String sessionId = session.getId();
        System.err.println("Session(" + sessionId + ") is error.");
    }

    @SneakyThrows
    @OnMessage
    public void onMessage(Session session, String message) {
        String sessionId = session.getId();
        System.err.println("Session(" + sessionId + ") is received: " + message);
        session.getBasicRemote().sendText("ECHO -> " + message);
    }

    @SneakyThrows
    public void broadcastMessage(String message) {
        SESSIONS.forEach((sessionId, session) -> {
            try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (Exception e) {
                System.err.println("Session(" + sessionId + ") is error.");
            }
        });
    }

    @SneakyThrows(IOException.class)
    public void sendMessage(String sessionId, String message) {
        Session session = SESSIONS.get(sessionId);
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
