package com.coderlong.javacore.thread.multi.threadlocal;

/**
 * 线程局部变量(ThreadLocal)互不干扰场景
 * @author Long Qiong
 * @create 2019/4/3
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        //启动2个线程，2个线程共享同一个Account
        Account at = new Account("初始名");
        //虽然2个线程共享同一个账户，但由于账户名是ThreadLocal的，
        //所以2个线程各自拥有该账户名的一个副本，
        //因此在i==6之后，将看到线程访问同一个账户时出现不同的账户名
        new MyTest(at, "线程甲").start();
        new MyTest(at, "线程乙").start();
    }
}

class Account{
    //定义个ThreadLocal变量，该变量是一个线程局部变量
    //每个线程都会保留该变量的一个副本
    private ThreadLocal<String> name = new ThreadLocal<>();

    public Account(String name){
        this.name.set(name);
        //用于访问当前线程的name副本的值
        System.out.println("----" + this.name.get() + "----" + Thread.currentThread().getName());
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}

class MyTest extends Thread{
    private Account account;
    public MyTest(Account account, String name){
        super(name);
        this.account = account;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            if(i == 6){
                account.setName(getName());
            }
            System.out.println(account.getName() + "账户的i值：" + i);
        }
    }
}
