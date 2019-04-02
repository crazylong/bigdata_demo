package com.coderlong.pattern.command;

import org.junit.Test;

/**
 * 命令模式测试
 * @author Long Qiong
 * @create 2019/3/25
 */
public class TestCommand {
    @Test
    public void test01(){
        int[] args = new int[]{1, 2, 3, 4};
        Command c1 = new MultiProcess();
        Command c2 = new SumProcess();
        process(args, c1);
        process(args, c2);
    }

    /**
     *
     */
    @Test
    public void test02(){
        int[] args = new int[]{1, 2, 3, 4};
        process(args, arr -> {
            int result = 0;
            for (int a : arr) {
                result += a;
            }
            System.out.println(result);
        });
    }

    public void process(int[] args, Command command){
        command.process(args);
    }
}
