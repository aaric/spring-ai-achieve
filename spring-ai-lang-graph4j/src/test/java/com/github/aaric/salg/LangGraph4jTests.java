package com.github.aaric.salg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphRepresentation;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.state.AgentState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        result.ifPresent(state -> log.info("{}", state.data()));
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
        result.ifPresent(state -> log.info("{}", state.data()));
    }
}
