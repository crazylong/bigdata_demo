package com.coderlong.javacore.thread.multi.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列
 * @author Long Qiong
 * @create 2019/4/2
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(2);
        bq.put("java");
        bq.put("java");
        bq.put("java");//阻塞
       //bq.add("java");//抛异常
        //System.out.println(bq.offer("java"));//返回false
    }
}
