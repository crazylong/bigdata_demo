package com.coderlong.javacore.thread.single;

/**
 * @author Long Qiong
 * @create 2019/3/29
 */
public class PriorityTest extends Thread{
    public PriorityTest(String name){
        super(name);
    }
    @Override
    public void run(){
        for (int i=0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "->优先级->" + currentThread().getPriority() + ",循环变量->" + i);
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setPriority(6);
        for (int i = 0; i < 30; i++) {
            if(i== 10){
                PriorityTest low = new PriorityTest("低级");
                low.start();
                System.out.println("低级创建之初的优先级:" + low.getPriority());
                low.setPriority(MIN_PRIORITY);
            }

            if(i== 20){
                PriorityTest high = new PriorityTest("高级");
                high.start();
                System.out.println("高级创建之初的优先级:" + high.getPriority());
                high.setPriority(MAX_PRIORITY);
            }
        }
    }


}
