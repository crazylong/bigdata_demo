package com.coderlong.javacore.thread.multi.communication.bysynchronized;

import java.util.Objects;

/**
 * 同步代码块与同步方法的线程通信 wait(), notify, notifyAll()
 * @author Long Qiong
 * @create 2019/4/1
 */
public class Account {
    private String accountNo;
    private double balance;

    /**是否有存款*/
    private boolean flag = false;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public synchronized void draw(double drawAmount) {
        try {
            if (flag) {
                System.out.println(Thread.currentThread().getName() + "取钱成功！吐出钞票:" + drawAmount);
                balance -= drawAmount;
                System.out.println(Thread.currentThread().getName() + "，余额：" + balance);
                flag = false;
                notifyAll();
            } else {
                wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void deposit(double depositAmount){
        try {
            if(flag){
                wait();
            } else {
                System.out.println(Thread.currentThread().getName() + "存钱成功！存入钞票:" + depositAmount);
                balance += depositAmount;
                System.out.println(Thread.currentThread().getName() + "，余额：" + balance);
                flag = true;
                notifyAll();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof com.coderlong.javacore.thread.multi.fault.Account))
            return false;
        com.coderlong.javacore.thread.multi.fault.Account account = (com.coderlong.javacore.thread.multi.fault.Account) o;
        return Objects.equals(getAccountNo(), account.getAccountNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNo());
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

}
