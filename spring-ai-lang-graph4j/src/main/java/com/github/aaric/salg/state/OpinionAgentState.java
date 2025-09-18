package com.github.aaric.salg.state;

import org.bsc.langgraph4j.state.AgentState;
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
public class OpinionAgentState extends AgentState {

    //    public static final String MESSAGES_KEY = "messages";
    public static final String MESSAGE_KEY = "messages";

    public static final Map<String, Channel<?>> SCHEMA = Map.of(
//            MESSAGES_KEY, Channels.appender(ArrayList::new)
            MESSAGE_KEY, Channels.appender(ArrayList::new)
    );

    public static String step(Integer index) {
        return "step" + index;
    }

    public OpinionAgentState(Map<String, Object> initData) {
        super(initData);
    }

//    public List<String> messages() {
//        return this.<List<String>>value(MESSAGES_KEY)
//                .orElse(List.of());
//    }

    public String message() {
        return this.<String>value(MESSAGE_KEY)
                .orElse("");
    }
}
