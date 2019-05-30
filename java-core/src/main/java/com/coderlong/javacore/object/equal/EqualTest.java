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
        System.out.println(a == b);//true
        System.out.println(a == c);//true
        System.out.println(d == e);//false
        System.out.println("hello" == d);//false
        System.out.println("hello" == new String("hello"));//false
        System.out.println("hello" == "hello");//true
        System.out.println("hello" == f);//true

        Integer i1 = 10;
        Integer i2 = 10;
        System.out.println(i1==i2);//true

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3==i4);//false

        Integer.valueOf(128);
    }
}
