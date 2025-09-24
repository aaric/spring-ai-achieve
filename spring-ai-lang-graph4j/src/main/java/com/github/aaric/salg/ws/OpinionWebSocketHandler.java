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
 * OpinionWebSocketHandler
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Slf4j
@Component
public class OpinionWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String chatId = UriComponentsBuilder.fromUri(session.getHandshakeInfo().getUri())
                .build().getQueryParams().getFirst("chatId");

        sessions.put(chatId, session);

        System.err.println("Session(" + chatId + ") is opened.");

        return session.receive()
                .map(msg -> "ECHO -> " + msg.getPayloadAsText())
                .map(session::textMessage)
                .flatMap(msg -> session.send(Mono.just(msg)))
                .then();
    }

    public void broadcastMessage(String message) {
        sessions.forEach((requestId, session) -> {
            try {
                session.send(Mono.just(session.textMessage(message))).subscribe();
            } catch (Exception e) {
                System.err.println("Session(" + requestId + ") is error.");
            }
        });
    }

    public void sendMessage(String chatId, String message) {
        WebSocketSession session = sessions.get(chatId);
        if (session != null) {
            session.send(Mono.just(session.textMessage(message))).subscribe();
        }
    }
}
