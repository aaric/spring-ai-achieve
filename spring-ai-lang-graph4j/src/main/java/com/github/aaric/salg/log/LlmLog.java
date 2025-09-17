package com.github.aaric.salg.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LlmLog
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LlmLog {

    public static final String LLM_LOG_KEY = "llm-log";

    private Integer id;

    private String requestId;

    private String systemPrompt;

    private String userPrompt;

    private String output;

    private String toolJson;

    private String exception;

    public LlmLog(String requestId, String systemPrompt, String userPrompt, String output, String toolJson) {
        this.requestId = requestId;
        this.systemPrompt = systemPrompt;
        this.userPrompt = userPrompt;
        this.output = output;
        this.toolJson = toolJson;
    }

    public LlmLog(String requestId, String systemPrompt, String userPrompt, String exception) {
        this.requestId = requestId;
        this.systemPrompt = systemPrompt;
        this.userPrompt = userPrompt;
        this.exception = exception;
    }
}
