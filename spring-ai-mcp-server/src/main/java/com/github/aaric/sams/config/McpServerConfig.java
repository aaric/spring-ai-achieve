package com.github.aaric.sams.config;

import com.github.aaric.sams.ai.SimpleContentService;
import com.github.aaric.sams.ai.SimpleWeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * McpServerConfig
 *
 * @author Aaric
 * @version 0.2.0-SNAPSHOT
 */
@Configuration
public class McpServerConfig {

    @Bean
    ToolCallbackProvider simpleContentTool(SimpleContentService simpleContentService, SimpleWeatherService simpleWeatherService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(simpleContentService, simpleWeatherService)
                .build();
    }
}
