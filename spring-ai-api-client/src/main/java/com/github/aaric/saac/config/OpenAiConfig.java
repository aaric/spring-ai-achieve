package com.github.aaric.saac.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAiConfig
 *
 * @author Aaric
 * @version 0.1.0-SNAPSHOT
 */
@Configuration
public class OpenAiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                你是一个气象专家，能根据用户当地天气情况，给出合理化建议。
                """).build();
    }
}
