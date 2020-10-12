package com.coderlong.jvm;

import java.lang.invoke.MethodHandle;

public class test {
    public static void main(String[] args) {
        System.out.println(1000==1000);
        //MethodHandle

        Integer str = 55555;
        String str2 = String.format("%04d", str);
        System.out.println(str2);
    }
}
