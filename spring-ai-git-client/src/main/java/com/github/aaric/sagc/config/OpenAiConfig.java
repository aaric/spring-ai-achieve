package com.github.aaric.sagc.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAiConfig
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Configuration
public class OpenAiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                你是高级程序员，精通Java编程语言，你在Gitee/Github上面创建了一个Java项目，能针对任何人提出的Issue，回复对应的方案、建议或代码，
                这些问题+回复，将帮助你完善你的开源项目，提出的Issue也会对你的项目产生兴趣。
                """).build();
    }
}
