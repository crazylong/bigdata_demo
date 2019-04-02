package com.coderlong.javacore.generic.upper.apple;

/**
 * 类型形参上限
 * @author Long Qiong
 * @create 2019/3/27
 */
public class Apple2<T extends Number> {
    public static void main(String[] args) {
        //Apply2<String> a1 = new Apply2<>();
        Apple2<Integer> a2 = new Apple2<>();
        Apple2<Double> a3 = new Apple2<>();
    }
}
