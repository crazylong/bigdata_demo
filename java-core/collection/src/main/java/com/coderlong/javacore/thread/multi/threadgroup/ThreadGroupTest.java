package com.coderlong.javacore.thread.multi.threadgroup;

/**
 * @author Long Qiong
 * @create 2019/4/2
 */
public class ThreadGroupTest {
    public static void main(String[] args) {
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println("主线程组的名字：" + mainGroup.getName());
        System.out.println("主线程组是否后台线程：" + mainGroup.isDaemon());
        new MyThread("主线程主的线程").start();

        ThreadGroup tg = new ThreadGroup("新线程组");

        tg.setDaemon(true);
        System.out.println("新线程组是否后台线程：" + tg.isDaemon());
        MyThread mt1 =  new MyThread(tg, "新线程组的线程甲");
        //tg.uncaughtException(mt1, new Exception());
//        mt1.setUncaughtExceptionHandler((t, e)->{
//            System.out.println("异常线程：" + t.getName());
//            e.printStackTrace();
//        });
        mt1.start();

        new MyThread(tg, "新线程组的线程乙").start();
    }
}

class MyThread extends Thread{
    public MyThread(String name){
        super(name);
    }

    public MyThread(ThreadGroup tg, String name){
        super(tg, name);
    }

    @Override
    public void run(){
//        String s = "abc";
//        Integer.valueOf(s);
        for (int i = 0; i < 100; i++) {

            System.out.println(getName() + "线程的i变量->" + i);
        }
    }
}
