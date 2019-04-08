package com.coderlong.javacore.thread.multi.fault;

import org.junit.Test;

/**
 * 多线程不正确场景演示
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawTest {
    /**
     * 多线程场景测试，余额会出现负数
     */
    @Test
    public void test01(){
        Account account = new Account("0001", 1000);

        new DrawThread("线程1", account, 800).start();
        new DrawThread("线程2", account, 800).start();
    }
}
