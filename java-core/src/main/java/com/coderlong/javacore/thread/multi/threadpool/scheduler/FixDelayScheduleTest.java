package com.coderlong.javacore.thread.multi.threadpool.scheduler;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 从上一次任务结束之后开始计时
 */
public class FixDelayScheduleTest {
    public static void main(String[] args) {
        Executors.newScheduledThreadPool(3).scheduleWithFixedDelay(() -> {
                    System.out.println(Thread.currentThread().getName() + "->" + new Date());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 0, 2, TimeUnit.SECONDS
        );
    }
}
