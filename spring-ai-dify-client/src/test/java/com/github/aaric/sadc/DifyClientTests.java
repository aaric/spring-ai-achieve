package com.github.aaric.sadc;

import com.github.aaric.sadc.config.DifyProperties;
import io.github.imfangs.dify.client.DifyChatflowClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * DifyClientTests
 *
 * @author Aaric
 * @version 0.14.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DifyClientTests {

    private final DifyProperties difyProperties;

    @Disabled
    @Test
    public void testChatFlow() throws Exception {
        DifyChatflowClient chatFlowClient = DifyClientFactory.createChatWorkflowClient(difyProperties.getBaseUrl(),
                difyProperties.getAppKey().get("ai-joke-campus"));

        ChatMessage message = ChatMessage.builder()
                .query("你好，请介绍一下自己")
                .user("user-123")
                .responseMode(ResponseMode.BLOCKING)
                .build();

        ChatMessageResponse response = chatFlowClient.sendChatMessage(message);
        log.debug("conversationId={}", response.getConversationId());
        log.debug("answer={}", response.getAnswer());
        log.debug("messageId={}", response.getMessageId());
    }
}
