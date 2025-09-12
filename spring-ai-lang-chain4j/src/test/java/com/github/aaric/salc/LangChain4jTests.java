package com.github.aaric.salc;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * LangChain4jAppTests
 *
 * @author Aaric
 * @version 0.16.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LangChain4jTests {

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String baseUrl;

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String modelName;

    private final ChatModel chatModel;

    @Test
    public void testCustomPromptText() {
        OpenAiChatModel customChatModel = OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
        log.debug("{}", customChatModel.chat("你是谁？你的版本？"));
    }

    @Test
    public void testPromptMessage1() {
        UserMessage userMessage = UserMessage.from("你是谁？你的版本？");
        ChatResponse chatResponse = chatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.debug("{}", aiMessage.text());
    }

    @Test
    public void testPromptMessage2() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("这个图片是啥？")
//                ImageContent.from("https://baikebcs.bdimg.com/baike-react/common/logo-baike.svg")
        );
        ChatResponse chatResponse = chatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.debug("{}", aiMessage.text());
    }

    @Test
    public void testPromptMessage3() {
        SystemMessage systemMessage = SystemMessage.from("你是一个笑话大王。");
        UserMessage userMessage = UserMessage.from(
                TextContent.from("讲个笑话")
        );
        ChatResponse chatResponse = chatModel.chat(systemMessage, userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.debug("{}", aiMessage.text());
    }
}
