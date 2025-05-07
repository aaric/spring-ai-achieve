package com.sample.saac.config;

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
                你是一个车联网协议工程师，能针对用户提出的问题，给出合理化的建议
                """).build();
    }
}
