package com.github.aaric.salg.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aaric.salg.agent.OpinionJudgeAgent;
import com.github.aaric.salg.agent.OpinionProcessAgent;
import com.github.aaric.salg.log.NodeLog;
import com.github.aaric.salg.state.OpinionAgentState;
import com.github.aaric.salg.ws.AgentLogWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.async.AsyncGenerator;
import org.bsc.langgraph4j.*;
import org.bsc.langgraph4j.action.AsyncEdgeAction;
import org.bsc.langgraph4j.checkpoint.MemorySaver;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private final ObjectMapper objectMapper;
    private final StringRedisTemplate stringRedisTemplate;

    private final OpinionJudgeAgent opinionJudgeAgent;
    private final OpinionProcessAgent opinionProcessAgent;

    private final AgentLogWebSocketHandler opinionWebSocketHandler;

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

    private AsyncGenerator<NodeOutput<OpinionAgentState>> createOutputStream(String question) throws GraphStateException {
        StateGraph<OpinionAgentState> workflow = createWorkflowGraph();
        MemorySaver saver = new MemorySaver();
        CompileConfig config = CompileConfig.builder()
                .checkpointSaver(saver)
                .build();
        CompiledGraph<OpinionAgentState> app = workflow.compile(config);
        app.setMaxIterations(10);
        return app.stream(Map.of("input", question), RunnableConfig.builder().build());
    }

    public void invokeStream(String question) throws GraphStateException {
        AsyncGenerator<NodeOutput<OpinionAgentState>> stream = createOutputStream(question);
        stream.forEach(output -> {
            System.err.printf("%b, %b, %s, %s\n", output.isSTART(), output.isEND(), output.node(), output.state());
        });
    }

    public void invokeStream(String question, String chatId, String requestId) throws GraphStateException {
        AsyncGenerator<NodeOutput<OpinionAgentState>> stream = createOutputStream(question);
        stream.forEach(output -> {
            // 节点日志
            NodeLog nodeLog = new NodeLog(requestId);
            if (output.isSTART()) {
                nodeLog.setMessage("流程开始");
            } else if (output.isEND()) {
                nodeLog.setMessage("流程结束");
            } else {
                nodeLog.setNodeName(output.node());
                nodeLog.setMessage("%s开始工作".formatted(nodeLog.getNodeName()));
            }

            // 发送日志消息
            try {
                opinionWebSocketHandler.sendMessage(chatId, objectMapper.writeValueAsString(nodeLog));
            } catch (JsonProcessingException e) {
                log.error("json error", e);
            }
        });
    }
}
