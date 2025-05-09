package com.github.aaric.samc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * McpClientTests
 *
 * @author Aaric
 * @version 0.3.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class McpClientTests {

//    private final OpenAiChatModel openAiChatModel;
//    private final ToolCallbackProvider toolCallbackProvider;

    private final ChatClient chatClient;

    @Test
    public void testPromptText() {
//        ChatClient chatClient = ChatClient.builder(openAiChatModel).build();
        System.err.println(chatClient.prompt().user("你是谁").call().content());
    }

    @Test
    public void testMcpClient() {
//        ChatClient chatClient = ChatClient.builder(openAiChatModel)
//                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
//                .build();
//        System.err.println(chatClient.prompt().user("推荐一些技术内容").call().content());
        System.err.println(chatClient.prompt().user("你有一些最好的技术内容吗").call().content());
    }
}
