package com.github.aaric.salg.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aaric.salg.log.LlmLog;
import com.github.aaric.salg.util.ChatMessageFormatUtil;
import com.github.aaric.salg.util.MDCRequestIdUtil;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * RedisChatModelListener
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReidsChatModelListener implements ChatModelListener {

    private final ObjectMapper objectMapper;

    private final StringRedisTemplate stringRedisTemplate;

//    private ListOperations<String, String> listOperations;
//
//    @PostConstruct
//    public void init() {
//        listOperations = stringRedisTemplate.opsForList();
//    }

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        log.info("onRequest: {}", requestContext.chatRequest());
    }

    @SneakyThrows
    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        // LLM输入
        ChatRequest chatRequest = responseContext.chatRequest();
        String systemPrompt = ChatMessageFormatUtil.formatSystemMessage(chatRequest.messages());
        String userPrompt = ChatMessageFormatUtil.formatUserMessage(chatRequest.messages());

        // LLM工具调用
        List<Map<String, Object>> toolMessages = ChatMessageFormatUtil.computeToolMessage(chatRequest.messages());

        // LLM输出
        ChatResponse chatResponse = responseContext.chatResponse();
        String output = chatResponse.aiMessage().text();

        // 日志
        String toolJson = null;
        try {
            toolJson = new ObjectMapper().writeValueAsString(toolMessages);
        } catch (JsonProcessingException e) {
            log.error("onResponse exception", e);
        }
        LlmLog llmLog = new LlmLog(MDCRequestIdUtil.getRequestId(), systemPrompt, userPrompt, output, toolJson);
        String llmLogJson = objectMapper.writeValueAsString(llmLog);
//        listOperations.rightPush(LlmLog.LLM_LOG_KEY, llmLogJson);
        stringRedisTemplate.convertAndSend(LlmLog.LLM_LOG_KEY, llmLogJson);
//        System.err.println(llmLogJson);

        log.info("onResponse: {}", responseContext.chatResponse());
    }

    @SneakyThrows
    @Override
    public void onError(ChatModelErrorContext errorContext) {
        // LLM输入
        ChatRequest chatRequest = errorContext.chatRequest();
        String systemPrompt = ChatMessageFormatUtil.formatSystemMessage(chatRequest.messages());
        String userPrompt = ChatMessageFormatUtil.formatUserMessage(chatRequest.messages());

        // LLM异常
        String exception = errorContext.error().getMessage();

        // 日志
        LlmLog llmLog = new LlmLog(MDCRequestIdUtil.getRequestId(), systemPrompt, userPrompt, exception);
        String llmLogJson = objectMapper.writeValueAsString(llmLog);
//        listOperations.rightPush(LlmLog.LLM_LOG_KEY, llmLogJson);
        stringRedisTemplate.convertAndSend(LlmLog.LLM_LOG_KEY, llmLogJson);
//        System.err.println(llmLogJson);

        log.error("onError: {}", errorContext.error().getMessage());
    }
}
