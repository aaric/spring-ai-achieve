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

    @Test
    public void testWorkflowCooking() throws Exception {
        StateGraph<AgentState> workflow = new StateGraph<>(AgentState::new)
                .addNode("step1", node_async(state -> {
                    log.debug("step1: {}", state);
                    return Map.of("step1", "洗菜");
                }))
                .addNode("step2", node_async(state -> {
                    log.debug("step2: {}", state);
                    return Map.of("step2", "切菜");
                }))
                .addNode("step3", node_async(state -> {
                    log.debug("step3: {}", state);
                    return Map.of("step3", "炒菜");
                }))
                .addEdge(START, "step1")
                .addEdge("step1", "step2")
                .addEdge("step2", "step3")
                .addEdge("step3", END);

        CompiledGraph<AgentState> app = workflow.compile();
        GraphRepresentation graph = app.getGraph(GraphRepresentation.Type.PLANTUML, "炒菜智能体");
        log.debug("graph plantuml: {}", graph.content());
        Optional<AgentState> result = app.invoke(Map.of("input", "西红柿"));
        result.ifPresent(state -> log.info("{}", state.data()));
    }
}
