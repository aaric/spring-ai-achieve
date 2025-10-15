package com.github.aaric.salg.demo.opinion.config;

import com.github.aaric.salg.demo.opinion.chat.OpinionJudgeService;
import com.github.aaric.salg.demo.opinion.chat.OpinionProcessService;
import com.github.aaric.salg.demo.opinion.tool.WeatherTool;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4jOpinionConfig
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LangChain4jOpinionConfig {

    @Bean
    public OpinionJudgeService opinionJudgeService(ChatModel chatModel, WeatherTool weatherTool) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
        return AiServices.builder(OpinionJudgeService.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(messageId -> chatMemory)
                .tools(weatherTool)
                .build();
    }

    @Bean
    public OpinionProcessService opinionAdviceService(ChatModel chatModel, WeatherTool weatherTool) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
        return AiServices.builder(OpinionProcessService.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(messageId -> chatMemory)
                .tools(weatherTool)
                .build();
    }
}
