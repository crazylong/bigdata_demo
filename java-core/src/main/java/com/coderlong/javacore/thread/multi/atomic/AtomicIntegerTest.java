package com.coderlong.javacore.thread.multi.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Long Qiong
 * @create 2019/4/4
 */
public class AtomicIntegerTest {
    private static final int THREAD_COUNT = 20;
//    public static volatile int count = 0;
//    public static void increase(){
//        count++;
//    }

    public static AtomicInteger count = new AtomicInteger(0);
    public static void increase(){
        count.incrementAndGet();
        //count.getAndIncrement();
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable r = ()->{
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            };
            threads[i] = new Thread(r);
            threads[i].start();
        }
//        while (Thread.activeCount() > 2){
////            System.out.println(Thread.activeCount());
////            System.out.println(Thread.currentThread().getName());
//            Thread.yield();
//        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}

