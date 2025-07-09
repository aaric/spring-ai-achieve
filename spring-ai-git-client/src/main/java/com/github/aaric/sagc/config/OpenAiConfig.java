package com.github.aaric.sagc.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAiConfig
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Configuration
public class OpenAiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                你是一个Git仓库管理员。
                """).build();
    }
}
