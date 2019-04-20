package com.coderlong.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * JConsole监控内存
 * @author Long Qiong
 * @create 2019/4/20
 */
public class JConsoleTest {
//    public JConsoleTest(){
//        byte[] b1 = new byte[128*1024];
//    }

    public byte[] b1 = new byte[128*1024];

    public static void main(String[] args) {
        try {
            Thread.sleep(5000);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("start...");
        fill(1000);
    }

    private static void fill(int n) {
        List<JConsoleTest> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e){
                e.printStackTrace();
            }
            list.add(new JConsoleTest());
        }
    }
}
