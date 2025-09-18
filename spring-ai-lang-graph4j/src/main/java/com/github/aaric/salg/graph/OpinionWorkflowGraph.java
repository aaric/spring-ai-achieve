package com.github.aaric.salg.graph;

import com.github.aaric.salg.agent.OpinionJudgeAgent;
import com.github.aaric.salg.agent.OpinionProcessAgent;
import com.github.aaric.salg.state.OpinionAgentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.action.AsyncEdgeAction;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.aaric.salg.state.OpinionAgentState.step;
import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * OpinionWorkflow
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpinionWorkflowGraph {

    private final OpinionJudgeAgent opinionJudgeAgent;
    private final OpinionProcessAgent opinionProcessAgent;

    public OpinionAgentState invoke(String question) throws GraphStateException {
        StateGraph<OpinionAgentState> workflow = new StateGraph<>(OpinionAgentState::new)
                .addNode(step(1), node_async(opinionJudgeAgent))
                .addNode(step(2), node_async(opinionProcessAgent))
                .addEdge(START, step(1))
                .addConditionalEdges(step(1),
                        AsyncEdgeAction.edge_async(state -> {
                            String type = state.message();
                            return type.equals("正面") ? "end" : step(2);
                        }),
                        Map.of(
                                step(2), step(2),
                                "end", END
                        )
                )
                .addEdge(step(2), END);

        CompiledGraph<OpinionAgentState> app = workflow.compile();
//        GraphRepresentation plantuml = app.getGraph(GraphRepresentation.Type.PLANTUML, "舆情识别智能体");
//        log.debug("opinion plantuml: {}", plantuml.content());
        return app.invoke(Map.of("input", question)).orElse(null);
    }
}
