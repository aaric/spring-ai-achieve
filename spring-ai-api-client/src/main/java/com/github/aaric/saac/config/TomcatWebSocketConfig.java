package com.github.aaric.saac.config;

import com.github.aaric.saac.ws.TomcatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * TomcatWebSocketConfig
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
//@Configuration
//@EnableWebSocket
@RequiredArgsConstructor
public class TomcatWebSocketConfig implements WebSocketConfigurer {

    private final TomcatWebSocketHandler tomcatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tomcatWebSocketHandler, "/ws/chat")
                .setAllowedOrigins("*");
    }
}
