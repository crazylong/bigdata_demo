package com.coderlong.javacore.thread.multi.guava;

class SubThread implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}