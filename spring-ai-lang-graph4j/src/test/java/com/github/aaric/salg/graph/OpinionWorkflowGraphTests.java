package com.github.aaric.salg.graph;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * OpinionWorkflowGraphTests
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OpinionWorkflowGraphTests {

    private final OpinionWorkflowGraph opinionWorkflowGraph;

    @Test
    public void testInvoke() throws Exception {
        log.info("{}", opinionWorkflowGraph.invoke("今天天气很好"));
//        log.info("{}", opinionWorkflowGraph.invoke("今天肚子不舒服"));
//        log.info("{}", opinionWorkflowGraph.invoke("今天武汉的天气，会影响我的身体状况").data());
    }
}
