package com.github.aaric.salc.config;

import com.github.aaric.salc.chat.TestChatService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4j 配置
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
@Configuration
@RequiredArgsConstructor
public class LangChain4jConfig {

    private final ChatModel chatModel;

    @Bean
    public TestChatService testChatService() {
//        return AiServices.create(TestChatService.class, chatModel);
//        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
//                .chatMemoryStore(null)
                .build();
        return AiServices.builder(TestChatService.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(messageId -> chatMemory)
                .build();
    }
}
