package com.github.aaric.salc;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * LangChain4jToolTests
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LangChain4jToolTests {

    private final ChatModel chatModel;

    private TestToolService testToolService;

    @BeforeEach
    public void setUp() {
        testToolService = AiServices.builder(TestToolService.class)
                .chatModel(chatModel)
                .tools(new MathTool())
                .hallucinatedToolNameStrategy(request ->
                        ToolExecutionResultMessage.from(request, "错误：没有`%s`工具".formatted(request.name())))
                .build();
    }

    /*@Test
    public void testChat1() {
//        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(MathTool.class);
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("add")
                .description("对给定的 2 个数值求和")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("a", "数值")
                        .addStringProperty("b", "数值")
                        .required("a", "b")
                        .build())
                .build();
        ToolExecutionRequest toolExecutionRequest = ToolExecutionRequest.builder()
                .id("add")
                .name("add")
                .arguments("{\"a\":\"1.22\",\"b\":\"100.52\"}")
                .build();
        String toolExecutionResult = "100";
        ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.from(toolExecutionRequest, toolExecutionResult);
        ChatRequest request = ChatRequest.builder()
                .messages(List.of(new UserMessage("1.22 + 100.52 等于多少？"), toolExecutionResultMessage))
//                .toolSpecifications(toolSpecifications)
                .toolSpecifications(toolSpecification)
                .build();
        ChatResponse response = chatModel.chat(request);
        log.debug("{}", response.aiMessage().text());
    }*/

    @Test
    public void testChat2() {
        log.debug("{}", testToolService.chat("1.22 + 100.52 等于多少？"));
        log.debug("{}", testToolService.chat("1.22 + abc 等于多少？"));
    }

    public interface TestToolService {
        String chat(String question);
    }

    public class MathTool {

        @Tool("对给定的 2 个数值求和")
        double add(@P("数值") double a, @P("数值") double b) {
//            return a + b;
            return a + b + 100;
        }
    }
}
