package com.coderlong.javacore.object.equal;

/**
 * @author Long Qiong
 * @create 2019/3/30
 */
public class EqualTest {
    public static void main(String[] args) {
        int a = 65;
        float b = 65.0f;
        char c = 'A';

        String d = new String("hello");
        String e = new String("hello");
        String f = "hello";

        //基本类型比较
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(d == e);
        System.out.println("hello" == d);
        System.out.println("hello" == new String("hello"));
        System.out.println("hello" == "hello");
        System.out.println("hello" == f);

    }
}
