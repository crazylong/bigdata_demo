package com.coderlong.jvm;

/**
 * 引用计数法
 * 不能清除对象之间相互引用演示
 * @author Long Qiong
 * @create 2019/4/20
 */
public class ReferenceCount {
    public ReferenceCount(){
        byte[] b = new byte[20*1024*1024];
    }

    public Object instance;

    public static void main(String[] args) {
        ReferenceCount r1 = new ReferenceCount();
        ReferenceCount r2 = new ReferenceCount();

        r1.instance = r2;
        r2.instance = r1;
        System.gc();
    }
}
