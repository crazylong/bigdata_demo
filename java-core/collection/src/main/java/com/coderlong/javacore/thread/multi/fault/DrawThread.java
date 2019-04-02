package com.coderlong.javacore.thread.multi.fault;

/**
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawThread extends Thread{
    private Account account;
    private double drawAmount;

    public DrawThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }

    @Override
    public void run(){

        if(account.getBalance() >= drawAmount){
            System.out.println(getName() + "取钱成功！吐出钞票:" + drawAmount);
            account.setBalance(account.getBalance() - drawAmount);
            System.out.println(currentThread().getName() + "，余额：" + account.getBalance());
        } else {
            System.out.println(currentThread().getName() + "，余额不足，取款金额：" + drawAmount + "，余额：" + account.getBalance());
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
