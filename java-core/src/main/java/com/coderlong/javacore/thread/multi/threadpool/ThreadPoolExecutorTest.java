package com.coderlong.javacore.thread.multi.threadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


class Task implements Callable<String> {
    private String filename;

    public Task(String filename) {
        this.filename = filename;
    }
    
    @Override
    public String call() throws Exception {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("完成 "+ filename);
        return "返回值" + filename;
    }
}

/**
 * 创建一个线程池，将50个任务分配进去，并发量最大为5
 * 运行结束后，输出每个线程返回的结束
 * title:ThreadPoolExecutorTest
 * descript:
 * @author liuzh
 * @date 2016年5月18日 下午8:16:26
 */
public class ThreadPoolExecutorTest {
    
    public void start() throws InterruptedException, ExecutionException {
        /**
         * 创建线程池，并发量最大为5
         * LinkedBlockingDeque，表示执行任务或者放入队列
         */
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 10, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //存储线程的返回值
        List<Future<String>> results = new LinkedList<Future<String>>();
        
        for (int i = 0; i < 10; i++) {
            Task task = new Task(String.valueOf(i));
            System.out.println("放入线程池：" + i);
            //调用submit可以获得线程的返回值
            Future<String> result = tpe.submit(task);
            results.add(result);
            
        }

        //此函数表示不再接收新任务，
        //如果不调用，awaitTermination将一直阻塞
        tpe.shutdown();
        //1天，模拟永远等待
        System.out.println(tpe.awaitTermination(1, TimeUnit.DAYS));

        //输出结果
        for (int i = 0; i < 10; i++) {
            System.out.println(results.get(i).get());
        }

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutorTest test = new ThreadPoolExecutorTest();
        test.start();
    }

}