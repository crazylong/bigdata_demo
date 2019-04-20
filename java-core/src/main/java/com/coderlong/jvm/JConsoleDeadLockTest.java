package com.coderlong.jvm;

/**
 * JConsole检测死锁
 * @author Long Qiong
 * @create 2019/4/20
 */
public class JConsoleDeadLockTest {
//    public static void main(String[] args) {
//        Object o1 = new Object();
//        Object o2 = new Object();
//        Runnable r1 = ()->{
//            synchronized (o1){
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (o2){
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + "->hello");
//                }
//            }
//        };
//
//        new Thread(r1, "t1").start();
//        new Thread(r1, "t2").start();
//    }

    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        new Thread(new DeadLockTest(o1, o2)).start();
        new Thread(new DeadLockTest(o2, o1)).start();
    }
}

class DeadLockTest implements Runnable{
    private Object o1;
    private Object o2;
    public DeadLockTest(Object o1, Object o2){
        this.o1 = o1;
        this.o2=o2;
    }
    @Override
    public void run() {
        synchronized (o1){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2){
                System.out.println(Thread.currentThread().getName() + "->hello");
            }
        }
    }
}
