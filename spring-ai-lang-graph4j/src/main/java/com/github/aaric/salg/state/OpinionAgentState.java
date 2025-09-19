package com.github.aaric.salg.state;

import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * OpinionAgentState
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
public class OpinionAgentState extends AgentState {
//public class OpinionAgentState extends MessagesState<String> {

    public static final String MESSAGES_KEY = "messages";
    public static final String JUDGE_KEY = "judge";
    public static final String PROCESS_KEY = "process";

    public static final Map<String, Channel<?>> SCHEMA = Map.of(
            MESSAGES_KEY, Channels.<String>appender(ArrayList::new),
            JUDGE_KEY, Channels.base(() -> "正面"),
            PROCESS_KEY, Channels.base((oldValue, newValue) -> newValue)
    );

    public static String step(Integer index) {
        return "step" + index;
    }

    public OpinionAgentState(Map<String, Object> initData) {
        super(initData);
    }

    public List<String> messages() {
        return this.<List<String>>value(MESSAGES_KEY)
                .orElse(List.of());
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
