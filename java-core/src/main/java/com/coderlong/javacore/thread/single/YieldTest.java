package com.coderlong.javacore.thread.single;

/**
 * vs sleep:可以让当前线程暂停，但不会阻塞该线程，只是将线程转入就绪状态
 * @author Long Qiong
 * @create 2019/3/29
 */
public class YieldTest extends Thread{
    public YieldTest(String name){
        super(name);
    }
    @Override
    public void run(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            if(i == 20){
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        YieldTest y1 = new YieldTest("高级");
        y1.setPriority(MAX_PRIORITY);
        y1.start();


        YieldTest y2 = new YieldTest("低级");
        y1.setPriority(MIN_PRIORITY);
        y2.start();
    }
}
