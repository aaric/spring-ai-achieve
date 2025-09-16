package com.github.aaric.salg.listener;

import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RedisChatModelListener
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Component
public class RedisChatModelListener implements ChatModelListener {

    private static String formatSystemMessage(List<ChatMessage> messages) {
        String systemPrompt = null;
        SystemMessage systemMessage = messages.stream()
                .filter(o -> o instanceof SystemMessage)
                .map(o -> (SystemMessage) o)
                .findFirst()
                .orElse(null);
        if (null != systemMessage) {
            systemPrompt = systemMessage.text();
        }
        return systemPrompt;
    }

    private static String formatUserMessage(List<ChatMessage> messages) {
        String userPrompt = null;
        UserMessage userMessage = messages.stream()
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
        return userPrompt;
    }

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        log.info("onRequest: {}", requestContext.chatRequest());
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        // LLM输入
        ChatRequest chatRequest = responseContext.chatRequest();
        String systemPrompt = formatSystemMessage(chatRequest.messages());
        String userPrompt = formatUserMessage(chatRequest.messages());

        // LLM输出
        ChatResponse chatResponse = responseContext.chatResponse();
        String output = chatResponse.aiMessage().text();

        // 日志
        System.err.printf("hash=%s, systemPrompt=%s, user=%s, output=%s\n", this.hashCode(),
                systemPrompt, userPrompt, output);

        log.info("onResponse: {}", responseContext.chatResponse());
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.error("onError: {}", errorContext.error().getMessage());
    }
}
