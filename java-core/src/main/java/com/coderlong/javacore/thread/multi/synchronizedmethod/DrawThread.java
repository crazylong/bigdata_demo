package com.coderlong.javacore.thread.multi.synchronizedmethod;

import com.coderlong.javacore.thread.multi.synchronizedmethod.Account;

/**
 * synchronized method demo
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawThread extends Thread{
    private Account account;
    private double  drawAmount;

    public DrawThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }

    @Override
    public void run(){
        account.draw(drawAmount);
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
