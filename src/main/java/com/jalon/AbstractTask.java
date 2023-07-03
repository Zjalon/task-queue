package com.jalon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 任务队列抽象类
 */
public abstract class AbstractTask implements Runnable {
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 任务消息
     */
    private final List<String> message;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 任务耗时(单位秒)
     */
    private long taskDuration;

    public AbstractTask() {
        this.taskName = UUID.randomUUID().toString();
        this.taskType = "default";
        this.taskDuration = 0L;
        this.message = new ArrayList<>();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public long getTaskDuration() {
        return taskDuration;
    }

    void setTaskDuration(long taskDuration) {
        this.taskDuration = taskDuration;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStartTime() {
        return startTime;
    }

    void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getMessage() {
        return message;
    }

   protected void setMessage(String message) {
        this.message.add(message);
    }

    @Override
    public String toString() {
        return "{" +
                "taskType='" + taskType + '\'' +
                ", taskName='" + taskName + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", message=" + message.toString() +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", taskDuration=" + taskDuration +
                '}';
    }
}
