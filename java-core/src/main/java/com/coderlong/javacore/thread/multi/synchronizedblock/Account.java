package com.coderlong.javacore.thread.multi.synchronizedblock;

import java.util.Objects;

/**
 * @author Long Qiong
 * @create 2019/3/31
 */
public class Account {
    private String accountNo;
    private double balance;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Account))
            return false;
        Account account = (Account) o;
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
