package com.jalon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务执行类
 */
public class TaskService {
    private static final Logger log = LogManager.getLogger(TaskService.class);
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final LinkedTaskQueue<AbstractTask> queue= new LinkedTaskQueue<AbstractTask>();
    private final ThreadPoolExecutor executor;

    private TaskService() {
        this.executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue, t -> new Thread(t, "jalon-task-pool"));
        this.start();
    }

    public void addTask(AbstractTask task) {
        queue.offer(task);
    }

    public void start() {
        int maxRetries = 3;
        executor.execute(() -> {
            while (true) {
                AbstractTask task = null;
                try {
                    task = (AbstractTask) queue.take();
                    log.info("{}-[{}]-开始", task.getTaskType(), task.getTaskName());
                    long startTime = System.currentTimeMillis();
                    boolean success = executeTaskWithRetries(task, maxRetries);
                    long endTime = System.currentTimeMillis();
                    if (success) {
                        task.setStartTime(df.format(new Date(startTime)));
                        task.setEndTime(df.format(new Date(endTime)));

                        task.setTaskDuration((endTime - startTime) / 1000);
                        log.info("{}-[{}]-结束", task.getTaskType(), task.getTaskName());
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    assert task != null;
                    log.error("{}-[{}]-错误:{}", task.getTaskType(), task.getTaskName(), e.getMessage());

                    task.setErrorMsg(e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    private boolean executeTaskWithRetries(AbstractTask task, int maxRetries) throws Exception {
        for (int retry = 1; retry <= maxRetries; retry++) {
            try {
                task.run();
                return true;
            } catch (Exception e) {
                log.info("{}-[{}]-异常:{}", task.getTaskType(), task.getTaskName(), e.getMessage());
                if (retry >= maxRetries) {
                    throw new Exception("超出最大重试次数:" + e.getMessage());
                }
            }
        }
        return false;
    }
}
