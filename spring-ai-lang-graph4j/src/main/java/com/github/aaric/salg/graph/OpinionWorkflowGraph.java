package com.github.aaric.salg.graph;

import com.github.aaric.salg.agent.OpinionJudgeAgent;
import com.github.aaric.salg.agent.OpinionProcessAgent;
import com.github.aaric.salg.state.OpinionAgentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.async.AsyncGenerator;
import org.bsc.langgraph4j.*;
import org.bsc.langgraph4j.action.AsyncEdgeAction;
import org.bsc.langgraph4j.checkpoint.MemorySaver;
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

    private StateGraph<OpinionAgentState> createWorkflowGraph() throws GraphStateException {
        StateGraph<OpinionAgentState> workflow = new StateGraph<>(OpinionAgentState::new)
                .addNode(step(1), node_async(opinionJudgeAgent))
                .addNode(step(2), node_async(opinionProcessAgent))
                .addEdge(START, step(1))
                .addConditionalEdges(step(1),
                        AsyncEdgeAction.edge_async(state -> {
                            String emotion = state.judge();
                            return emotion.equals("正面") ? "end" : step(2);
                        }),
                        Map.of(
                                step(2), step(2),
                                "end", END
                        )
                )
                .addEdge(step(2), END);
        return workflow;
    }

    public OpinionAgentState invoke(String question) throws GraphStateException {
        StateGraph<OpinionAgentState> workflow = createWorkflowGraph();
        MemorySaver saver = new MemorySaver();
        CompileConfig config = CompileConfig.builder()
                .checkpointSaver(saver)
                .build();
        CompiledGraph<OpinionAgentState> app = workflow.compile(config);
//        GraphRepresentation plantuml = app.getGraph(GraphRepresentation.Type.PLANTUML, "舆情识别智能体");
//        log.debug("opinion plantuml: {}", plantuml.content());
        app.setMaxIterations(10);
        return app.invoke(Map.of("input", question), RunnableConfig.builder().build())
                .orElse(null);
    }

    public void invokeStream(String question) throws GraphStateException {
        StateGraph<OpinionAgentState> workflow = createWorkflowGraph();
        MemorySaver saver = new MemorySaver();
        CompileConfig config = CompileConfig.builder()
                .checkpointSaver(saver)
                .build();
        CompiledGraph<OpinionAgentState> app = workflow.compile(config);
        app.setMaxIterations(10);
        AsyncGenerator<NodeOutput<OpinionAgentState>> stream = app.stream(Map.of("input", question), RunnableConfig.builder().build());
        stream.forEach(output -> {
            System.err.printf("%b, %b, %s, %s\n", output.isSTART(), output.isEND(), output.node(), output.state());
        });
    }
}
