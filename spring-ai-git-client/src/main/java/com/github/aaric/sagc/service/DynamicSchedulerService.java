package com.github.aaric.sagc.service;

import java.time.Duration;
import java.util.Set;

/**
 * 动态调度任务 Service接口
 *
 * @author Aaric
 * @version 0.15.0-SNAPSHOT
 */
public interface DynamicSchedulerService {

    /**
     * 列出所有任务ID
     *
     * @return 任务ID
     */
    Set<String> queryTaskIdSet();

    /**
     * 获取任务状态
     *
     * @param taskId 任务ID
     * @return 任务状态
     */
    String getTaskState(String taskId);

    /**
     * 添加任务
     *
     * @param taskId         任务ID
     * @param task           任务
     * @param cronExpression 任务执行表达式
     * @return 状态
     * @throws Exception 异常
     */
    boolean addTask(String taskId, Runnable task, String cronExpression) throws Exception;

    /**
     * 添加任务
     *
     * @param taskId   任务ID
     * @param task     任务
     * @param interval 任务执行间隔
     * @return 状态
     * @throws Exception 异常
     */
    boolean addTask(String taskId, Runnable task, Duration interval) throws Exception;

    /**
     * 添加任务
     *
     * @param taskId      任务ID
     * @param task        任务
     * @param interval    任务执行间隔
     * @param isFixedRate 是否固定间隔
     * @return 状态
     * @throws Exception 异常
     */
    boolean addTask(String taskId, Runnable task, Duration interval, boolean isFixedRate) throws Exception;

    /**
     * 更新任务
     *
     * @param taskId         任务ID
     * @param task           任务
     * @param cronExpression 任务执行表达式
     * @return 状态
     * @throws Exception 异常
     */
    boolean updateTask(String taskId, Runnable task, String cronExpression) throws Exception;

    /**
     * 移除任务
     *
     * @param taskId 任务ID
     * @return 状态
     * @throws Exception 异常
     */
    boolean removeTask(String taskId) throws Exception;
}
