package com.coderlong.javacore.thread.single;

/**
 * 1.继承Thread类
 * @author Long Qiong
 * @create 2019/3/28
 */
public class FirstThread extends Thread {
    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(i + ":" + currentThread().getName());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + ":" + currentThread().getName());
            if(i == 20){
                new FirstThread().start();
                new FirstThread().start();
            }
        }
    }
}
