package com.coderlong.javacore.thread.multi.communication.bylock;

/**
 * lock锁的线程通信 await(), signal(), signalAll()
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawThread extends   Thread {


    private Account account;

    private double drawAmount;

    public DrawThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.draw(drawAmount);
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(double drawAmount) {
        this.drawAmount = drawAmount;
    }
}
