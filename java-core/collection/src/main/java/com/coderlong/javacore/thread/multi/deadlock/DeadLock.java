package com.coderlong.javacore.thread.multi.deadlock;


/**
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DeadLock implements Runnable {
    A a = new A();
    B b = new B();

    public void init(){
        Thread.currentThread().setName("主线程");
        a.foo(b);
        System.out.println("进入了主线程之后......");
    }

    @Override
    public void run() {
        Thread.currentThread().setName("副线程");
        b.bar(a);
        System.out.println("进入了副线程之后.....");
    }

    public static void main(String[] args) {
        DeadLock d1 = new DeadLock();
        new Thread(d1).start();
        d1.init();
    }
}

class A{
    public synchronized void foo(B b){
        System.out.println("当前线程名：" + Thread.currentThread().getName() + "，进入A实例的foo方法");
        try {
            Thread.sleep(200);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("当前线程名：" + Thread.currentThread().getName() + "企图调用B实例的last方法");
        b.last();
    }

    public synchronized void last(){
        System.out.println("进入了A类的last()方法内部");
    }
}

class B{
    public synchronized void bar(A a){
        System.out.println("当前线程名：" + Thread.currentThread().getName() + "，进入B实例的bar方法");
        try {
            Thread.sleep(200);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("当前线程名：" + Thread.currentThread().getName() + "企图调用A实例的last方法");
        a.last();
    }
    public synchronized void last(){
        System.out.println("进入了B类的last()方法内部");
    }
}
