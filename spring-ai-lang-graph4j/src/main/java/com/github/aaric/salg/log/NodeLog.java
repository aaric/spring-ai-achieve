package com.github.aaric.salg.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * AgentLog
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Deprecated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeLog {

    private Long id;

    private String requestId;

    private String nodeName;

    private String message;

    public NodeLog(String requestId) {
        this.id = Instant.now().toEpochMilli();
        this.requestId = requestId;
    }
}
