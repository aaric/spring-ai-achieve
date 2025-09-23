package com.github.aaric.salg.agent;

import com.github.aaric.salg.chat.OpinionJudgeService;
import com.github.aaric.salg.state.OpinionAgentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.aaric.salg.state.OpinionAgentState.step;

/**
 * OpinionJudgeAgent
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpinionJudgeAgent implements NodeAction<OpinionAgentState> {

    private final OpinionJudgeService opinionJudgeService;

    @Override
    public Map<String, Object> apply(OpinionAgentState state) throws Exception {
        log.info("{}: {}", step(1), state);
        String input = (String) state.value("input").orElse("");
        return Map.of(OpinionAgentState.JUDGE_KEY, opinionJudgeService.chat(input));
    }
}
