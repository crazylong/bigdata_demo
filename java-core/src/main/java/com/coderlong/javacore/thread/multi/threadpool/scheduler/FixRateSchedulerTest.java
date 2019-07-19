package com.coderlong.javacore.thread.multi.threadpool.scheduler;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 从上一次任务启动后开始计时，等上一个任务执行完再执行
 * 若上一次任务执行的时间大于设置的周期(period)，设置的周期将不起效果
 */
public class FixRateSchedulerTest {
    public static void main(String[] args) {
        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(() -> {
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
