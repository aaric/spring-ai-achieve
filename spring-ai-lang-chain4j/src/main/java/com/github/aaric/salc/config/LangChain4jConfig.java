package com.github.aaric.salc.config;

import com.github.aaric.salc.chat.TestChatService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

/**
 * LangChain4j 配置
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
//@Configuration
@RequiredArgsConstructor
public class LangChain4jConfig {

    private final ChatModel chatModel;

    @Bean
    public TestChatService testChatService() {
        return AiServices.create(TestChatService.class, chatModel);
    }
}
