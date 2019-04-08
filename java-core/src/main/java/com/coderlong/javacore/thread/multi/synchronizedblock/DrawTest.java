package com.coderlong.javacore.thread.multi.synchronizedblock;

import org.junit.Test;

/**
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawTest {

    /**
     * 同步代码块测试
     * todo 有时候不会执行，原因不明
     */
    @Test
    public void test02(){
        Account account = new Account("0001", 1000);

        new DrawThread("线程1", account, 800).start();
        new DrawThread("线程2", account, 800).start();
    }
}
