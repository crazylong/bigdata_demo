package com.coderlong.javacore.thread.multi.lock;

/**
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawTest {

    public static void main(String[] args) {
        Account account = new Account("0001", 1000);

        new DrawThread("线程1", account, 800).start();
        new DrawThread("线程2", account, 800).start();
    }
}
