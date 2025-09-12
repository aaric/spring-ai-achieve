package com.github.aaric.salc.config;

import com.github.aaric.salc.chat.TestChatService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${bigmodel.api-key}")
    private String bigModelApiKey;

    private final ChatModel chatModel;

    private final StreamingChatModel streamingChatModel;

    /*@Bean
    public McpToolProvider mcpToolProvider() {
        McpTransport transport = new StreamableHttpMcpTransport.Builder()
                .url("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + bigModelApiKey)
                .logRequests(true)
                .logResponses(true)
                .build();
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .key("MyMCPClient")
                .transport(transport)
                .build();
        return McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();
    }*/

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
                .streamingChatModel(streamingChatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(messageId -> chatMemory)
//                .contentRetriever(null)
//                .toolProvider(mcpToolProvider())
                .build();
    }
}
