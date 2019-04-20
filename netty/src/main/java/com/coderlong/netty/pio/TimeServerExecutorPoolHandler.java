package com.coderlong.netty.pio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Long Qiong
 * @create 2019/4/15
 */
public class TimeServerExecutorPoolHandler {
    private Executor executor;
    public TimeServerExecutorPoolHandler(int poolSize, int queueSize){
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), poolSize, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    public void executor(Runnable runnable){
        executor.execute(runnable);
    }
}
