package com.coderlong.javacore.thread.multi.threadpool;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author Long Qiong
 * @create 2019/4/2
 */
public class RecursiveTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = new int[100];
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(20);
            total += arr[i];
        }
        System.out.println("total=" + total);
        //创建一个通用的线程池
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //提交可分解的CalTask任务
        Future<Integer> future = pool.submit(new CalTask(arr, 0, arr.length));
        //获取任务返回值
        System.out.println(future.get());
        pool.shutdown();

    }
}

class CalTask extends RecursiveTask<Integer>{
    private final int THRESHOLD = 20;
    private int[] arr;
    private int start;
    private int end;

    public CalTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum =0;
        if(end - start < THRESHOLD){
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        } else {
            int middle = (start + end)/2;
            CalTask left = new CalTask(arr, start, middle);
            CalTask right = new CalTask(arr, middle, end);
            //并行执行两个小任务
            left.fork();
            right.fork();
            //把两个小任务累加的结果合并起来
            return left.join() + right.join();
        }
    }
}
