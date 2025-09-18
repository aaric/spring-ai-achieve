package com.github.aaric.salg.agent;

import com.github.aaric.salg.service.OpinionProcessService;
import com.github.aaric.salg.state.SimpleAgentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.aaric.salg.state.SimpleAgentState.step;

/**
 * OpinionProcessAgent
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpinionProcessAgent implements NodeAction<SimpleAgentState> {

    private final OpinionProcessService opinionProcessService;

    @Override
    public Map<String, Object> apply(SimpleAgentState state) throws Exception {
        log.info("{}: {}", step(2), state);
        String input = (String) state.value("input").orElse("");
        return Map.of(SimpleAgentState.MESSAGES_KEY, opinionProcessService.chat(input));
    }
}
