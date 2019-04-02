package com.coderlong.javacore.thread.single;

/**
 * 后台线程
 * 为其它线程提供服务，当所有前台线程都死亡，后台线程自动死亡
 * @author Long Qiong
 * @create 2019/3/29
 */
public class DaemonThread extends Thread {
    @Override
    public void run(){
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
        }
    }

    public static void main(String[] args) {
        DaemonThread dt = new DaemonThread();
        dt.setDaemon(true);
        dt.start();

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
        }
    }
}
