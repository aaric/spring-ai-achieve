package com.github.aaric.salc;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.structured.Description;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * LangChain4jFormatTests
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LangChain4jFormatTests {

    private final ChatModel chatModel;

    private TestFormatService testFormatService;

    @BeforeEach
    public void setUp() {
        testFormatService = AiServices.builder(TestFormatService.class)
                .chatModel(chatModel)
                .build();
    }

    @Test
    public void testFormatPerson() {
        ResponseFormat responseFormat = ResponseFormat.builder()
                .type(ResponseFormat.JSON.type())
                .jsonSchema(JsonSchema.builder()
                        .name("Person")
                        .rootElement(JsonObjectSchema.builder()
                                .addStringProperty("name")
                                .addIntegerProperty("age")
                                .addNumberProperty("height")
                                .addBooleanProperty("married")
                                .required("name", "age", "height", "married")
                                .build()
                        )
                        .build()
                )
                .build();
        UserMessage userMessage = UserMessage.from("""
                John is 42 years old and lives an independent life.
                He stands 1.75 meters tall and carries himself with confidence.
                Currently unmarried, he enjoys the freedom to focus on his personal goals and interests.
                """);
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(List.of(userMessage))
                .responseFormat(responseFormat)
                .build();
        ChatResponse chatResponse = chatModel.chat(chatRequest);
        System.err.println(chatResponse.aiMessage().text());
    }

    @Test
    public void testFormatPersonObject() {
        Person p1 = testFormatService.chat("""
                Nick is 24 years young and lives an independent life.
                He stands 1.88 meters tall and carries himself with confidence.
                Currently unmarried, he enjoys the freedom to focus on his personal goals and interests.
                """);
        System.err.println(p1);
    }

    @Description("A person")
    public record Person(@Description("The name of the person") String name,
                  @Description("The age of the person") int age,
                  @Description("The height of the person") double height,
                  @Description("Whether the person is married") boolean married) {
    }

    public interface TestFormatService {
        Person chat(String question);
    }
}
