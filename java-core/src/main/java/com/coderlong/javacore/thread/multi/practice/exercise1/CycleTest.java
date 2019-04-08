package com.coderlong.javacore.thread.multi.practice.exercise1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CycleTest {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    public static void main(String[] args) {
        Runnable r1 = ()->{
            lock.lock();
            try{
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName() + "->" + i);
                }
            } finally {
                lock.unlock();
            }

        };

        Runnable r2 = ()->{
            lock.lock();
            try{
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "->" + i);
                }
            } finally {
                lock.unlock();
            }
        };

        for (int i = 0; i < 50; i++) {
            new Thread(r1, "子线程").start();
            new Thread(r2, "主线程").start();
        }
    }


}
