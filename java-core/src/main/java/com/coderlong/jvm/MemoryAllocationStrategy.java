package com.coderlong.jvm;

/**
 * 内存分配策略
 * 1.优先分配到eden
 * 2.大对象直接分配到老年代
 * 3.长期存活的对象分配到老年代
 * 4.空间担保
 * 5.动态对象年龄判断
 * @author Long Qiong
 * @create 2019/4/20
 */
public class MemoryAllocationStrategy {
    //1. -verbose:gc -XX:+PrintGCDetails （默认-XX:+UseParallelGC）
    //2. -verbose:gc -XX:+PrintGCDetails -XX:+UseSerialGC
    //3. -verbose:gc -XX:+PrintGCDetails -XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8
    //4. -verbose:gc -XX:+PrintGCDetails -XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:PretenuredSizeThreshold=3m
    public static void main(String[] args) {
//        byte[] b1 = new byte[2 * 1024 * 1024];
//        byte[] b2 = new byte[2 * 1024 * 1024];
//        byte[] b3 = new byte[2 * 1024 * 1024];
//
//        //eden区此时空间不够，触发minor gc 到 survivor区，
//        //survivor区也不够，触发空间担保，将b1,b2,b3移入 tenured gen, 然后b4进入eden
//        byte[] b4 = new byte[4 * 1024 * 1024];


        //-XX:PretenuredSizeThreshold=3m
        byte[] b5 = new byte[4 * 1024 * 1024];
        System.gc();
    }
}
