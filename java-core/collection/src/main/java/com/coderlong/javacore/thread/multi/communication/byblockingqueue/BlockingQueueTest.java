package com.coderlong.javacore.thread.multi.communication.byblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Long Qiong
 * @create 2019/4/2
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(1);
        new Producer(bq).start();
        new Producer(bq).start();
        new Producer(bq).start();
        new Consumer(bq).start();;

    }
}
