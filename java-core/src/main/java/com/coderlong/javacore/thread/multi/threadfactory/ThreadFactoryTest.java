package com.coderlong.javacore.thread.multi.threadfactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Long Qiong
 * @create 2019/4/4
 */
public class ThreadFactoryTest {
    public static void main(String[] args) {
       // Executor executor = new ThreadPoolExecutor();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("retryClient-pool-").build();

        ExecutorService exec = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),threadFactory);
        exec.submit(() -> {
            System.out.println("hi world");
        });
    }
}
