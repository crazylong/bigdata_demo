package com.coderlong.javacore.thread.multi.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Long Qiong
 * @create 2019/4/2
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        //创建一个具有固定线程数(6)的线程池
        ExecutorService pool = Executors.newFixedThreadPool(6);
        Runnable target = ()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "->" +i);
            }
        };
        pool.submit(target);
        pool.submit(target);
        new Thread(target).start();
        pool.shutdown();
    }
}
