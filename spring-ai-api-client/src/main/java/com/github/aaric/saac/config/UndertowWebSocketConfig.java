package com.github.aaric.saac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * UndertowWebSocketConfig
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Configuration
public class UndertowWebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
