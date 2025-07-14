package com.github.aaric.sagc.config;

import com.github.aaric.sagc.service.IssueTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务配置
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@Configuration
//@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {

    private final IssueTaskService issueTaskService;

    @Scheduled(fixedDelay = 1000)
    public void doSimpleAiTips() {
        System.err.println("*************** doSimpleAiTips ***************");
        System.err.printf("The total of issues answered is %d.\n", issueTaskService.doSimpleAiTips());
    }
}
