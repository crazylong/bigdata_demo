package com.coderlong.javacore.thread.multi.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 继承RecursiveAction实现“可分解”的任务
 * @author Long Qiong
 * @create 2019/4/2
 */
public class ForkJoinPoolTest{
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new PrintTest(0, 300));
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }
}

class PrintTest extends RecursiveAction{
    //每个小任务最大只打印50个数
    private final int SHRESHOLD = 50;
    private int start;
    private int end;
    public PrintTest(int start, int end){
        this.start = start;
        this.end = end;
    }
    @Override
    protected void compute() {
        //当end与start之间的差小于THRESHOLD是，开始打印
        if(end - start < SHRESHOLD){
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "->" +i);
            }
        } else {
            //当end与start之间差大于THRESHOLD，
            //将大任务分解成两个“小任务”
            int middle = (start + end)/2;
            PrintTest left = new PrintTest(start, middle);
            PrintTest right = new PrintTest(middle, end);
            //并行执行两个小任务
            left.fork();
            right.fork();
        }
    }
}
