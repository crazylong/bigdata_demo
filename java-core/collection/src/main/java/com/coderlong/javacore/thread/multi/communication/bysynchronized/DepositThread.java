package com.coderlong.javacore.thread.multi.communication.bysynchronized;

/**
 * 同步代码块与同步方法的线程通信 wait(), notify, notifyAll()
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DepositThread extends   Thread {


    private Account account;

    private double depositAmount;

    public DepositThread(String name, Account account, double depositAmount) {
        super(name);
        this.account = account;
        this.depositAmount = depositAmount;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.deposit(depositAmount);
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }
}
