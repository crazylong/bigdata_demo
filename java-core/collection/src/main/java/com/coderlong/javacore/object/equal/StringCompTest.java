package com.coderlong.javacore.object.equal;

import java.sql.SQLOutput;

/**
 * @author Long Qiong
 * @create 2019/3/30
 */
public class StringCompTest {
    public static void main(String[] args) {
        //s1直接引用常量池的"疯狂Java"
        String s1 = "疯狂Java";
        String s2 = "疯狂";
        String s3 = "Java";
        //s4后面的字符串可以在编译时就确定下来
        //s4直接引用常量池的"疯狂Java"
        String s4 = "疯狂" + "Java";
        //s5后面的字符串可以在编译时就确定下来
        //s5直接引用常量池的"疯狂Java"
        String s5 ="疯" + "狂" + "Java";
        //s6不能引用常量池的字符串
        //s6后面的字符串值不能在编译时就确定下来
        String s6 = s2 +s3;
        //s7引用堆内存中新创建的String对象
        String s7 = new String("疯狂Java");

        System.out.println(s1 == s4);//true
        System.out.println(s1 == s5);//true
        System.out.println(s1 == s6);//false
        System.out.println(s1 == s7);//false
        System.out.println(s1.equals(s7));//true
        //使用new String()创建的字符串对象是运行时创建出来的，
        //它被保存在运行时内存区(即堆内存)内，不会放入常量池
    }
}
