package com.coderlong.javacore.thread.multi.synchronizedmethod;

import com.coderlong.javacore.thread.multi.synchronizedmethod.Account;
import org.junit.Test;

/**
 * @author Long Qiong
 * @create 2019/4/1
 */
public class DrawTest {

    /**
     * 同步方法测试
     */
    @Test
    public void test02(){
        Account account = new Account("0001", 1000);

        new DrawThread("线程1", account, 800).start();
        new DrawThread("线程2", account, 800).start();
        StringBuilder sb = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
    }
}
