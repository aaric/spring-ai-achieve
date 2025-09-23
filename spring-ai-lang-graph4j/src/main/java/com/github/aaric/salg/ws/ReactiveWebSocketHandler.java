package com.github.aaric.salg.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ReactiveWebSocketHandler
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Slf4j
@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessions.add(session);

        System.err.println("Session(" + session.getId()/* + "-" + roomId + "-" + userId*/ + ") is opened.");

        return session.receive()
                .map(msg -> "ECHO -> " + msg.getPayloadAsText())
                .map(session::textMessage)
                .flatMap(msg -> session.send(Mono.just(msg)))
                .then();
    }
}
