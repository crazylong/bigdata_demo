package com.coderlong.pattern.command;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public class MultiProcess implements Command {
    @Override
    public void process(int[] args) {
        int multi = 1;
        for (int i : args) {
            multi *= i;
        }
        System.out.println("multi=" + multi);
    }
}
