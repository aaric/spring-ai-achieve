package com.github.aaric.sagc.service.impl;

import com.github.aaric.sagc.service.DynamicSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 动态调度任务 Service接口实现
 *
 * @author Aaric
 * @version 0.15.0-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicSchedulerServiceImpl implements DynamicSchedulerService {

    private final TaskScheduler scheduler;
    private final Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    @Override
    public Set<String> queryTaskIdSet() {
        return taskMap.keySet();
    }

    @Override
    public String getTaskState(String taskId) {
        if (CollectionUtils.isEmpty(taskMap)) {
            return null;
        }
//        return taskMap.get(taskId).state().name();
        ScheduledFuture<?> future = taskMap.get(taskId);
        if (future == null) {
            return null;
        }
        String stateName = null;
        if (future.isCancelled()) {
            stateName = "CANCELLED";
        } else if (future.isDone()) {
            stateName = "DONE";
        } else {
            stateName = "RUNNING";
        }
        return stateName;
    }

    @Override
    public boolean addTask(String taskId, Runnable task, String cronExpression) {
        if (taskMap.containsKey(taskId)) {
            return false;
        }

        ScheduledFuture<?> future = scheduler.schedule(task, new CronTrigger(cronExpression));
        taskMap.put(taskId, future);
        return true;
    }

    @Override
    public boolean addTask(String taskId, Runnable task, Duration interval) {
        return addTask(taskId, task, interval, false);
    }

    @Override
    public boolean addTask(String taskId, Runnable task, Duration interval, boolean isFixedRate) {
        if (taskMap.containsKey(taskId)) {
            return false;
        }

        PeriodicTrigger trigger = new PeriodicTrigger(interval);
        trigger.setFixedRate(isFixedRate);
        ScheduledFuture<?> future = scheduler.schedule(task, trigger);
        taskMap.put(taskId, future);
        return true;
    }

    @Override
    public boolean updateTask(String taskId, Runnable task, String cronExpression) {
        removeTask(taskId);
        return addTask(taskId, task, cronExpression);
    }

    @Override
    public boolean updateTask(String taskId, Runnable task, Duration interval) {
        removeTask(taskId);
        return addTask(taskId, task, interval);
    }

    @Override
    public boolean updateTask(String taskId, Runnable task, Duration interval, boolean isFixedRate) {
        removeTask(taskId);
        return addTask(taskId, task, interval, isFixedRate);
    }

    @Override
    public boolean removeTask(String taskId) {
        if (taskMap.containsKey(taskId)) {
            ScheduledFuture<?> future = taskMap.get(taskId);
            future.cancel(true);
            taskMap.remove(taskId);
            return true;
        }
        return false;
    }
}
