package com.github.aaric.salg;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphRepresentation;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.action.AsyncEdgeAction;
import org.bsc.langgraph4j.state.AgentState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * LangGraph4jTests
 *
 * @author Aaric
 * @version 0.18.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LangGraph4jTests {

    private final ChatModel chatModel;

    private static String step(Integer index) {
        return "step" + index;
    }

    @Test
    public void testWorkflowCooking() throws Exception {
        StateGraph<AgentState> workflow = new StateGraph<>(AgentState::new)
                .addNode(step(1), node_async(state -> {
                    log.debug("{}: {}", step(1), state);
                    return Map.of(step(1), "洗菜");
                }))
                .addNode(step(2), node_async(state -> {
                    log.debug("{}: {}", step(2), state);
                    return Map.of(step(2), "切菜");
                }))
                .addNode(step(3), node_async(state -> {
                    log.debug("{}: {}", step(3), state);
                    return Map.of(step(3), "炒菜");
                }))
                .addEdge(START, step(1))
                .addEdge(step(1), step(2))
                .addEdge(step(2), step(3))
                .addEdge(step(3), END);

        CompiledGraph<AgentState> app = workflow.compile();
        GraphRepresentation graph = app.getGraph(GraphRepresentation.Type.PLANTUML, "炒菜智能体");
        log.debug("cooking plantuml: {}", graph.content());
        Optional<AgentState> result = app.invoke(Map.of("input", "西红柿"));
        result.ifPresent(state -> log.debug("{}", state.data()));
    }

    @Test
    public void testWorkflowCoding() throws Exception {
        StateGraph<AgentState> workflow = new StateGraph<>(AgentState::new)
                .addNode(step(1), node_async(state -> {
                    log.debug("{}: {}", step(1), state);
                    return Map.of(step(1), "评估需求");
                }))
                .addNode(step(2), node_async(state -> {
                    log.debug("{}: {}", step(2), state);
                    return Map.of(step(2), "后端开发");
                }))
                .addNode(step(3), node_async(state -> {
                    log.debug("{}: {}", step(3), state);
                    return Map.of(step(3), "前端开发");
                }))
                .addNode(step(4), node_async(state -> {
                    log.debug("{}: {}", step(4), state);
                    return Map.of(step(4), "联调测试");
                }))
                .addEdge(START, step(1))
                .addEdge(step(1), step(2))
                .addEdge(step(1), step(3))
                .addEdge(step(2), step(4))
                .addEdge(step(3), step(4))
                .addEdge(step(4), END);

        CompiledGraph<AgentState> app = workflow.compile();
        GraphRepresentation graph = app.getGraph(GraphRepresentation.Type.PLANTUML, "项目开发智能体");
        log.debug("coking plantuml: {}", graph.content());
        Optional<AgentState> result = app.invoke(Map.of("input", "Web项目"));
        result.ifPresent(state -> log.debug("{}", state.data()));
    }

    private String chat(String systemPrompt, String userPrompt) {
        List<ChatMessage> messages = List.of(
                UserMessage.from(systemPrompt),
                UserMessage.from(userPrompt)
        );
        ChatResponse chatResponse = chatModel.chat(messages);
        return chatResponse.aiMessage().text();
    }

    @Test
    public void testWorkflowMonitor() throws Exception {
        StateGraph<AgentState> workflow = new StateGraph<>(AgentState::new)
                .addNode(step(1), node_async(state -> {
                    log.debug("{}: {}", step(1), state);
                    String input = (String) state.value("input").orElse("");
                    return Map.of("type", chat("你是一个舆情识别专家，你的任务是判断用户输入是正面情绪还是负面情绪，输出\"正面\"或者\"负面\"，不要输出其他解析内容。",
                            input));
                }))
                .addNode(step(2), node_async(state -> {
                    log.debug("{}: {}", step(2), state);
                    String input = (String) state.value("input").orElse("");
                    return Map.of("classify", chat("你是一个舆情处理专家，你的任务是将用户的问题派发到不同的部，如：医院、学校、银行。仅输出最终的结果，不需要解析内容。",
                            input));
                }))
                .addEdge(START, step(1))
                .addConditionalEdges(step(1),
                        AsyncEdgeAction.edge_async(state -> {
                            String type = (String) state.value("type").orElse("");
                            return type.equals("正面") ? "end" : step(2);
                        }),
                        Map.of(
                                step(2), step(2),
                                "end", END
                        )
                )
                .addEdge(step(2), END);

        CompiledGraph<AgentState> app = workflow.compile();
        GraphRepresentation graph = app.getGraph(GraphRepresentation.Type.PLANTUML, "舆情识别智能体");
        log.debug("monitor plantuml: {}", graph.content());
//        Optional<AgentState> result = app.invoke(Map.of("input", "今天肚子有点痛"));
        Optional<AgentState> result = app.invoke(Map.of("input", "今天天气很好"));
        result.ifPresent(state -> log.debug("{}", state.data()));
    }
}
