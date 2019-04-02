package com.coderlong.javacore.thread.single;

/**
 * 让一个线程等待另一个线程完成的方法
 * @author Long Qiong
 * @create 2019/3/28
 */
public class JoinThread extends Thread {

    public JoinThread(String name){
        super(name);
    }

    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(currentThread().getName() + "->" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new JoinThread("子线程1").start();

        for (int i = 0; i < 200; i++) {
            System.out.println(currentThread().getName() + "->" + i);
            if(i == 100){
                JoinThread jt = new JoinThread("被join的线程");
                jt.start();
                //jt为被join线程，将jt join到主线程(main 线程)，main线程必须等jt执行完后才执行
                jt.join();
            }
        }

    }
}
