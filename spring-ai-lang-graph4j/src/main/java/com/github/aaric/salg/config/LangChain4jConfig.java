package com.github.aaric.salg.config;

import com.github.aaric.salg.service.OpinionJudgeService;
import com.github.aaric.salg.service.OpinionProcessService;
import dev.langchain4j.data.message.ContentType;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4jConfig
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LangChain4jConfig {

//    private final ChatModel chatModel;

    @Bean
    public ChatModelListener chatModelListener() {
        return new ChatModelListener() {

            @Override
            public void onRequest(ChatModelRequestContext requestContext) {
                ChatRequest chatRequest = requestContext.chatRequest();
                String systemPrompt = null;
                SystemMessage systemMessage = chatRequest.messages().stream()
                        .filter(o -> o instanceof SystemMessage)
                        .map(o -> (SystemMessage) o)
                        .findFirst()
                        .orElse(null);
                if (null != systemMessage) {
                    systemPrompt = systemMessage.text();
                }
                String userPrompt = null;
                UserMessage userMessage = chatRequest.messages().stream()
                        .filter(o -> o instanceof UserMessage)
                        .map(o -> (UserMessage) o)
                        .findFirst()
                        .orElse(null);
                if (null != userMessage) {
                    userPrompt = userMessage.contents().stream()
                            .filter(o -> o.type() == ContentType.TEXT)
                            .map(o -> ((TextContent) o).text())
                            .findFirst()
                            .orElse(null);
                }
                System.err.printf("hash=%s, system=%s, user=%s\n", this.hashCode(),
                        systemPrompt, userPrompt);
                log.info("onRequest: {}", chatRequest);
            }

            @Override
            public void onResponse(ChatModelResponseContext responseContext) {
                ChatResponse chatResponse = responseContext.chatResponse();
                String output = chatResponse.aiMessage().text();
                System.err.printf("hash=%s, output=%s\n", this.hashCode(), output);
                log.info("onResponse: {}", chatResponse);
            }

            @Override
            public void onError(ChatModelErrorContext errorContext) {
                log.error("onError: {}", errorContext.error().getMessage());
            }
        };
    }

    @Bean
    public OpinionProcessService opinionAdviceService(ChatModel chatModel) {
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
    public OpinionJudgeService opinionJudgeService(ChatModel chatModel) {
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
