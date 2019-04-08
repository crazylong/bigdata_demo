package com.coderlong.javacore.lambda;

import com.coderlong.pattern.command.Command;
import com.coderlong.pattern.command.TestCommand;
import org.junit.Test;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public class MyLambdaTest {
    @Test
    public void test01(){
        int[] args = new int[]{1, 2, 3, 4};
        TestCommand tc = new TestCommand();
        tc.process(args, new Command() {
            double result = 1;
            @Override
            public void process(int[] array) {
                for (int i : array) {
                    result = Math.pow(i, result);
                }
                System.out.println("pow=" + result);
            }
        });

        tc.process(args, array->{
            int sum = 0;
            for (int i : array) {
                sum += i;
            }
            System.out.println("sum=" + sum);
        });
    }
}
