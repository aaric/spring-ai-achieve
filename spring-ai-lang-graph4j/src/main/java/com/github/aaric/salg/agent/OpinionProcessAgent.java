package com.github.aaric.salg.agent;

import com.github.aaric.salg.service.OpinionProcessService;
import com.github.aaric.salg.state.OpinionAgentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.aaric.salg.state.OpinionAgentState.step;

/**
 * OpinionProcessAgent
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpinionProcessAgent implements NodeAction<OpinionAgentState> {

    private final OpinionProcessService opinionProcessService;

    @Override
    public Map<String, Object> apply(OpinionAgentState state) throws Exception {
        log.info("{}: {}", step(2), state);
        String input = (String) state.value("input").orElse("");
        return Map.of(OpinionAgentState.PROCESS_KEY, opinionProcessService.chat(input));
    }
}
