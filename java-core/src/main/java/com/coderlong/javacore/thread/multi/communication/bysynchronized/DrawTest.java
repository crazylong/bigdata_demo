package com.coderlong.javacore.thread.multi.communication.bysynchronized;

/**
 * 同步代码块与同步方法的线程通信 wait(), notify, notifyAll()
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawTest {
    public static void main(String[] args) {
        Account account = new Account("123456", 1000);
       new DrawThread("取款者", account, 800).start();
       new DepositThread("存款者甲", account, 800).start();
        new DepositThread("存款者乙", account, 800).start();
        new DepositThread("存款者丙", account, 800).start();
    }
}
