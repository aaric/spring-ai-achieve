package com.github.aaric.salg.listener;

import dev.langchain4j.data.message.ContentType;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MdcChatModelListener
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Deprecated
@Slf4j
//@Component
public class MdcChatModelListener implements ChatModelListener {

    private final Map<String, ChatModelRequestContext> requestContexts = new ConcurrentHashMap<>();

    private String generateRequestId() {
        return "req-" + System.currentTimeMillis() + "-" +
                Thread.currentThread().getId();
    }

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        String requestId = MDC.get("requestId");
        if (requestId == null) {
            requestId = generateRequestId();
            MDC.put("requestId", requestId);
        }

        requestContexts.put(requestId, requestContext);

        log.info("onRequest: {}", requestContext.chatRequest());
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        String requestId = MDC.get("requestId");
        if (requestId != null && requestContexts.containsKey(requestId)) {
            ChatModelRequestContext originalRequest = requestContexts.get(requestId);

            // LLM输入
            ChatRequest chatRequest = originalRequest.chatRequest();
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

            // LLM输出
            ChatResponse chatResponse = responseContext.chatResponse();
            String output = chatResponse.aiMessage().text();

            // 日志
            System.err.printf("hash=%s, systemPrompt=%s, userPrompt=%s, output=%s\n", this.hashCode(),
                    systemPrompt, userPrompt, output);

            // 清理
            requestContexts.remove(requestId);
            MDC.remove("requestId");
        }

        log.info("onResponse: {}", responseContext.chatResponse());
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.error("onError: {}", errorContext.error().getMessage());
    }
}
