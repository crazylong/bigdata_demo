package com.coderlong.javacore.thread.multi.practice;

/**
 * 写2个线程，其中一个线程打印1~52，另一个线程打印A~Z，打印顺序应该是12A34B56C...5152Z
 * 方法2.synchronized, wait, nofityAll通信
 * @author Long Qiong
 * @create 2019/4/3
 */
public class PrintTest2{

    public static void main(String[] args) {
        PrintTest2 pt = new PrintTest2();
        new Thread(pt.r1).start();
        new Thread(pt.r2).start();
    }

    Runnable r1 = ()->{
        print1();
    };

    Runnable r2 = ()->{
        print2();
    };

    public synchronized void print1(){
        for (int i = 1; i < 53; i++) {
            System.out.print(i);
            if(i%2==0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                notifyAll();
            }
        }
    }

    public synchronized void print2(){

        for (int i = 65; i < 91; i++) {
            System.out.print((char)i);
            notifyAll();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
