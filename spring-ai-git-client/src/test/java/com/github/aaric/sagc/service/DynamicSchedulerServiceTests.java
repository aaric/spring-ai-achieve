package com.github.aaric.sagc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * DynamicSchedulerServiceTests
 *
 * @author Aaric
 * @version 0.15.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DynamicSchedulerServiceTests {

    private final DynamicSchedulerService dynamicSchedulerService;

    @Disabled
    @Test
    public void testAddTask() throws Exception {
        String taskId = "task-id-1";
        dynamicSchedulerService.addTask(taskId, () -> log.debug(taskId), "0/5 * * * * ?");
        dynamicSchedulerService.addTask("task-id-2", () -> log.debug("task-id-2"), Duration.ofSeconds(3));
        log.debug("{}", dynamicSchedulerService.queryTaskIdSet());
        log.debug("{}:{}", taskId, dynamicSchedulerService.getTaskState(taskId));
        TimeUnit.MINUTES.sleep(1);
    }

    @Disabled
    @Test
    public void testUpdateTask() throws Exception {
        String taskId = "task-id-1";
        dynamicSchedulerService.addTask(taskId, () -> log.debug("{}", taskId), "0/5 * * * * ?");
        TimeUnit.SECONDS.sleep(15);
        dynamicSchedulerService.updateTask(taskId, () -> log.debug("{}", taskId), "0/10 * * * * ?");
        TimeUnit.SECONDS.sleep(30);
        dynamicSchedulerService.removeTask(taskId);
        log.debug("{}", dynamicSchedulerService.queryTaskIdSet());
    }
}
