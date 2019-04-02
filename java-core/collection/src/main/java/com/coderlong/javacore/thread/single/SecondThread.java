package com.coderlong.javacore.thread.single;

/**
 * 2.实现Runnable
 * 程序所创建的Runnable对象只是线程的target，而多个线程可以共享同一个target，
 * 所以多个线程可以共享同一个线程类（实际上应该是线程的target类）的实际变量
 * @author Long Qiong
 * @create 2019/3/28
 */
public class SecondThread implements Runnable{
    private int i;
    @Override
    public void run(){
        for (; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
        }
    }

    public static void main(String[] args) {
        for (int j = 0; j < 100; j++) {
            System.out.println(j + ":" + Thread.currentThread().getName());
            if(j == 20){
                SecondThread st = new SecondThread();
                new Thread(st, "新线程1").start();
                new Thread(st, "新线程2").start();
            }
        }
    }
}
