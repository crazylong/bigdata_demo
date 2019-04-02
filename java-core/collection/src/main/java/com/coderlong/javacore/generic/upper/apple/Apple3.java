package com.coderlong.javacore.generic.upper.apple;

/**
 * 为类型形参设定多个上限（至多有一个父类上限，可以有多个接口上限），表明改类型必须是其父类的子类（是父类本身也行），并且实现多个上限接口
 * 与类同时继承父类、实现接口类似的是，为类型形参指定多个上限时，
 * 所有的接口上限必须位于类上限之后。
 * 也就是说，如果需要为类型形参指定类上限，类上限必须位于第一位
 * @author Long Qiong
 * @create 2019/3/27
 */
public class Apple3<T extends Number & java.io.Serializable> {
    public static void main(String[] args) {
        Apple3<Integer> a1 = new Apple3<>();
    }
}
