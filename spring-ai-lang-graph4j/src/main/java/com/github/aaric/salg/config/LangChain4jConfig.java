package com.github.aaric.salg.config;

import com.github.aaric.salg.service.OpinionProcessService;
import com.github.aaric.salg.service.OpinionJudgeService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4jConfig
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Configuration
@RequiredArgsConstructor
public class LangChain4jConfig {

    private final ChatModel chatModel;

    @Bean
    public OpinionProcessService opinionAdviceService() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
        return AiServices.builder(OpinionProcessService.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(messageId -> chatMemory)
                .build();
    }

    @Bean
    public OpinionJudgeService opinionJudgeService() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
        return AiServices.builder(OpinionJudgeService.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(messageId -> chatMemory)
                .build();
    }
}
