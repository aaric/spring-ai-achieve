package com.github.aaric.salg.config;

import com.github.aaric.salg.ws.OpinionWebSocketHandler;
import com.github.aaric.salg.ws.ReactiveWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * ReactiveWebSocketConfig
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Configuration
public class ReactiveWebSocketConfig {

    @Bean
    public HandlerMapping webSocketHandlerMapping(ReactiveWebSocketHandler reactiveWebSocketHandler,
                                                  OpinionWebSocketHandler opinionWebSocketHandler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/chat/{roomId}", reactiveWebSocketHandler);
        map.put("/ws/opinion/agent", opinionWebSocketHandler);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
