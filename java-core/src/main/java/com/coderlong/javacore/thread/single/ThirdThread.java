package com.coderlong.javacore.thread.single;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 3.使用Callable和Future创建线程
 * 3.1 Callable接口，可以看成Runnable接口的增强版，提供一个call方法作为线程的执行体，
 * 但call()方法比run()方法更强大：
 * 1)call方法可以有返回值
 * 2)call方法可以声明抛出异常
 *
 * 3.2 Future:
 *  Callable不能直接作为Thread的target，需要使用Future接口封装
 *  FutureTask为Future的实现类，同时实现了Runnable接口
 * @author Long Qiong
 * @create 2019/3/28
 */
public class ThirdThread {
    public static void main(String[] args) {
        //使用lambda表达式创建Callable对象
        //使用FutureTask封装Callable对象
        FutureTask<Integer> task = new FutureTask<>(()->{
            int i = 0;
            for (; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "->" + i);
            }
            //Callable对象返回值
            return i;
        });

        FutureTask<Integer> task2 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 20;
            }
        });

        FutureTask<Integer> task3 = new FutureTask<>((Callable<Integer>)()->{
            int i = 0;
            for (; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + "->" + i);
            }
            return i;
        });

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            if(i == 20){
                new Thread(task, "新线程1").start();
            }

        }

        try {

            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
