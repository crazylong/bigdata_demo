package com.coderlong.jvm;

import java.util.Scanner;

/**
 * JConsole监控线程
 * @author Long Qiong
 * @create 2019/4/20
 */
public class JConsoleThreadTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        new Thread(()->{
            while (true){

            }
        }, "while true").start();

        scanner.next();
        new Thread(()->{
            synchronized (JConsoleThreadTest.class){
                try {
                    JConsoleThreadTest.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "wait").start();
    }
}
