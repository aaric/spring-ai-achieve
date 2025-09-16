package com.github.aaric.salg.util;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.data.message.*;
import org.bsc.langgraph4j.langchain4j.util.ChatMessageUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 对话消息工具类
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
public class ChatMessageFormatUtil {

    /**
     * 提取系统消息
     *
     * @param messages 消息列表
     * @return 系统消息
     */
    public static String formatSystemMessage(List<ChatMessage> messages) {
        String systemPrompt = null;
        SystemMessage systemMessage = messages.stream()
                .filter(ChatMessageUtil::isSystemMessage)
                .map(o -> (SystemMessage) o)
                .findFirst()
                .orElse(null);
        if (null != systemMessage) {
            systemPrompt = systemMessage.text();
        }
        return systemPrompt;
    }

    /**
     * 提取用户消息
     *
     * @param messages 消息列表
     * @return 用户消息
     */
    public static String formatUserMessage(List<ChatMessage> messages) {
        String userPrompt = null;
        UserMessage userMessage = messages.stream()
                .filter(ChatMessageUtil::isUserMessage)
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

    /**
     * 提取AI消息中的工具执行请求
     *
     * @param messages 聊天消息列表
     * @return 工具执行请求列表
     */
    public static List<ToolExecutionRequest> formatAiMessage(List<ChatMessage> messages) {
        AiMessage aiMessage = messages.stream()
                .filter(ChatMessageUtil::isAiMessage)
                .map(o -> (AiMessage) o)
                .findFirst()
                .orElse(null);
        if (null != aiMessage) {
            return aiMessage.toolExecutionRequests();
        }
        return List.of();
    }

    /**
     * 提取工具执行结果消息
     *
     * @param messages 聊天消息列表
     * @return 工具执行结果消息
     */
    public static ToolExecutionResultMessage formatToolExecutionResultMessage(List<ChatMessage> messages) {
        return messages.stream()
                .filter(ChatMessageUtil::isToolExecutionResultMessage)
                .map(o -> (ToolExecutionResultMessage) o)
                .findFirst()
                .orElse(null);
    }

    /**
     * 计算工具执行消息
     *
     * @param messages 聊天消息列表
     * @return 工具执行消息列表
     */
    public static List<Map<String, Object>> computeToolMessage(List<ChatMessage> messages) {
        List<Map<String, Object>> toolMessages = new ArrayList<>();
        List<ToolExecutionRequest> toolRequests = formatAiMessage(messages);
        ToolExecutionResultMessage toolResultMessage = formatToolExecutionResultMessage(messages);
        if (!CollectionUtils.isEmpty(toolRequests)) {
            String result;
            for (ToolExecutionRequest toolRequest : toolRequests) {
                result = null;
                if (null != toolResultMessage && Objects.equals(toolRequest.id(), toolResultMessage.id())) {
                    result = toolResultMessage.text();
                }
                toolMessages.add(Map.of(
                        "id", toolRequest.id(),
                        "name", toolRequest.name(),
                        "arguments", toolRequest.arguments(),
                        "result", result));
            }
        }
        return toolMessages;
    }
}
