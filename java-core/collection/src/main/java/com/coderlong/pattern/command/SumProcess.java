package com.coderlong.pattern.command;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public class SumProcess implements Command {
    @Override
    public void process(int[] args) {
        int sum = 0;
        for (int i : args) {
            sum += i;
        }
        System.out.println("sum=" + sum);
    }
}
