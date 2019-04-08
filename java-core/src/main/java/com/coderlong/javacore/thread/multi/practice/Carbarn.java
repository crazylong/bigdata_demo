package com.coderlong.javacore.thread.multi.practice;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Long Qiong
 * @create 2019/4/3
 */
public class Carbarn {
    public static void main(String[] args) {

        Carbarn cb = new Carbarn();
        Runnable r1 = ()->{

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cb.in();
            }

        };

        Runnable r2 = ()->{
            while (true){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cb.out();
            }

        };

        try {
            cb.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r1, "进库A").start();
        new Thread(r1, "进库B").start();
        new Thread(r2, "出库C").start();
        new Thread(r2, "出库D").start();

    }


    /**
     * 3个车位
     * false 无车位; true 有车位
     */
    public boolean[] carbarn = new boolean[]{false, false, false};
    public BlockingQueue<Boolean> bq = new ArrayBlockingQueue<>(3);
    void init() throws InterruptedException {
        bq.put(true);
        bq.put(true);
        bq.put(true);
    }

    /**
     * 入库
     */
    public void in(){
        int index = ArrayUtils.indexOf(carbarn, true);
        if(index >= 0){
            carbarn[index] = false;
            System.out.println(Thread.currentThread().getName() + "已入库");
        } else {
            System.out.println(Thread.currentThread().getName() + "等待入库");
        }

        try {
            bq.put(true);
            System.out.println(Thread.currentThread().getName() + "已入库");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出库
     */
    public void out(){
        int index = ArrayUtils.indexOf(carbarn, false);
        if(index >= 0){
            carbarn[index] = true;
            System.out.println(Thread.currentThread().getName() + "已出库");
        } else {
            System.out.println(Thread.currentThread().getName() + "等待出库");
        }

        try {
            bq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test01(){
        int index = ArrayUtils.indexOf(carbarn, true);
        System.out.println(index);

    }
}
