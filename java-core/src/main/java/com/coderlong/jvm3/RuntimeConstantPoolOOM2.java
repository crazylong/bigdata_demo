package com.coderlong.jvm3;

/**
 * String.intern()返回引用的测试
 * 是一个本地方法，
 * 如果常量池中已经包含一个等于此String对象的字符串，则返回常代表池中这个字符串的String对象的引用；
 * 如果不包含，则将String对象包含的字符串加入常量池，并返回此String对象的引用
 * @author LongQiong
 * @date 2021/2/25
 */
public class RuntimeConstantPoolOOM2 {
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
