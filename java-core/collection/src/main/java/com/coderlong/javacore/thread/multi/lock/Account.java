package com.coderlong.javacore.thread.multi.lock;


import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁(ReentrantLock) demo
 * @author Long Qiong
 * @create 2019/4/1
 */
public class Account {
    private static final Lock   lock = new ReentrantLock();
    private              String accountNo;
    private              double balance;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public void draw(double drawAmount){
        lock.lock();
        try {
            if(balance >= drawAmount){
                System.out.println(Thread.currentThread().getName() + "取钱成功！吐出钞票:" + drawAmount);
                balance -= drawAmount;
                System.out.println(Thread.currentThread().getName() + "，余额：" + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + "，余额不足，取款金额：" + drawAmount + "，余额：" + balance);
            }
        } catch (Exception e){

        } finally {
            lock.unlock();
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

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
