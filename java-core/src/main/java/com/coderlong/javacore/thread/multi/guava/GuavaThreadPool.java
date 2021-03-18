package com.coderlong.javacore.thread.multi.guava;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author
 * @date
 */
public class GuavaThreadPool {
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ExecutorService pool = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        for(int i = 0; i< 10; i++){
            pool.execute(new SubThread());
        }
    }
}