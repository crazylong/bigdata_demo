package com.coderlong.javacore.thread.multi.communication.byblockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author Long Qiong
 * @create 2019/4/2
 */
public class Producer extends Thread {
    private BlockingQueue<String> bq;
    public Producer(BlockingQueue<String> bq){
        this.bq = bq;
    }

    @Override
    public void run() {
        String[] source = new String[]{"Java", "Struts", "Spring"};
        for (int i = 0; i < 999999; i++) {
            System.out.println(getName() + "生产者准备生产集合元素！");
            try {
                Thread.sleep(200);
                //尝试放入元素，如果队列已满，则线程被阻塞
                bq.put(source[i%3]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + "生产完成：" + bq);
        }
    }
}
