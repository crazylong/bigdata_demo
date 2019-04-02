package com.coderlong.javacore.thread.multi.synchronizedblock;

/**
 * synchronized block demo
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

        //使用account作为同步监视器，任何线程进入下面代码块之前
        //必须先获得对account账户的锁定--其它线程无法获得锁，也就无法修改它
        //这种做法符合：加锁->编辑->释放锁的逻辑
        synchronized (account){
            if(account.getBalance() >= drawAmount){
                System.out.println(getName() + "取钱成功！吐出钞票:" + drawAmount);
                account.setBalance(account.getBalance() - drawAmount);
                System.out.println(currentThread().getName() + "，余额：" + account.getBalance());
            } else {
                System.out.println(currentThread().getName() + "，余额不足，取款金额：" + drawAmount + "，余额：" + account.getBalance());
            }
        }
        //释放锁
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
