package com.github.aaric.salg.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ReactiveWebSocketHandler
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Slf4j
@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessions.put(session.getId(), session);

        String path = UriComponentsBuilder.fromUri(session.getHandshakeInfo().getUri())
                .build().getPath();
        String roomId = path.substring(path.lastIndexOf("/") + 1);
        String userId = UriComponentsBuilder.fromUri(session.getHandshakeInfo().getUri())
                .build().getQueryParams().getFirst("userId");

        System.err.println("Session(" + session.getId() + "-" + roomId + "-" + userId + ") is opened.");

        return session.receive()
                .map(msg -> "ECHO -> " + msg.getPayloadAsText())
                .map(session::textMessage)
                .flatMap(msg -> session.send(Mono.just(msg)))
                .then();
    }

    public void broadcastMessage(String message) {
        sessions.forEach((sessionId, session) -> {
            try {
                session.send(Mono.just(session.textMessage(message))).subscribe();
            } catch (Exception e) {
                System.err.println("Session(" + sessionId + ") is error.");
            }
        });
    }

    public void sendMessage(String sessionId, String message) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null) {
            session.send(Mono.just(session.textMessage(message))).subscribe();
        }
    }
}
