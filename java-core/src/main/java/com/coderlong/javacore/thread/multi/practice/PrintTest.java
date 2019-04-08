package com.coderlong.javacore.thread.multi.practice;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写2个线程，其中一个线程打印1~52，另一个线程打印A~Z，打印顺序应该是12A34B56C...5152Z
 * 方法1.采用Lock Condition通信
 * @author Long Qiong
 * @create 2019/4/3
 */
public class PrintTest {
    static Lock     lock = new ReentrantLock();
    static Condition cond = lock.newCondition();
    static Runnable r1   = ()->{
        lock.lock();
        try {
            for (int i = 1; i < 53; i++) {
                System.out.print(i);
                if(i%2==0){
                    cond.await();
                } else {
                    cond.signalAll();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    };

    static Runnable r2 = ()->{
        lock.lock();
        try{
            for (int i = 65; i < 91; i++) {
                System.out.print((char)i);
                cond.signalAll();
                cond.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    };

    public static void main(String[] args) {
        new Thread(r1).start();
        new Thread(r2).start();
    }

    @Test
    public void test01(){
        System.out.println(Integer.valueOf('Z'));
        int i = 65;
        char c = (char)i;
        System.out.println(c);
    }

}
