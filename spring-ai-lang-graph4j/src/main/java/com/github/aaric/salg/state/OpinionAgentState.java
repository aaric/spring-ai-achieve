package com.github.aaric.salg.state;

import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;

import java.util.ArrayList;
import java.util.Map;

/**
 * OpinionAgentState
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
//public class OpinionAgentState extends AgentState {
public class OpinionAgentState extends MessagesState<String> {

    public static final String JUDGE_KEY = "judge";
    public static final String PROCESS_KEY = "process";

    public static final Map<String, Channel<?>> SCHEMA = Map.of(
            JUDGE_KEY, Channels.<String>appender(ArrayList::new),
            PROCESS_KEY, Channels.<String>appender(ArrayList::new)
    );

    public static String step(Integer index) {
        return "step" + index;
    }

    public OpinionAgentState(Map<String, Object> initData) {
        super(initData);
    }

    public String judge() {
        return this.<String>value(JUDGE_KEY)
                .orElse("");
    }

    public String process() {
        return this.<String>value(PROCESS_KEY)
                .orElse("");
    }
}
