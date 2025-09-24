package com.github.aaric.salg.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.Instant;

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

    public static final String LLM_LOG_KEY = "llm-log2";

    private Long id;

    private String requestId;

    private String nodeName;

    private String systemPrompt;

    private String userPrompt;

    private String output;

    private String toolJson;

    private String exception;

    public LlmLog(String requestId, String systemPrompt, String userPrompt, String output, String toolJson) {
        this.id = Instant.now().toEpochMilli();
        this.requestId = requestId;
        this.systemPrompt = systemPrompt;
        this.userPrompt = userPrompt;
        this.output = output;
        this.toolJson = toolJson;
    }

    public LlmLog(String requestId, String systemPrompt, String userPrompt, String exception) {
        this.id = Instant.now().toEpochMilli();
        this.requestId = requestId;
        this.systemPrompt = systemPrompt;
        this.userPrompt = userPrompt;
        this.exception = exception;
    }

    public void setAgentName() {
        if (StringUtils.startsWithIgnoreCase(systemPrompt, "你是一个舆情识别专家")) {
            this.nodeName = "judge";
        } else if (StringUtils.startsWithIgnoreCase(systemPrompt, "你是一个舆情处理专家")) {
            this.nodeName = "process";
        } else {
            this.nodeName = "unknown";
        }
    }
}
