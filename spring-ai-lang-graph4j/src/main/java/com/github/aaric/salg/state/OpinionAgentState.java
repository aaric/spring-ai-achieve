package com.github.aaric.salg.state;

import org.bsc.langgraph4j.state.AgentState;

import java.util.Map;

/**
 * OpinionAgentState
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
public class OpinionAgentState extends AgentState {

    public OpinionAgentState(Map<String, Object> initData) {
        super(initData);
    }

    public static String step(Integer index) {
        return "step" + index;
    }
}
