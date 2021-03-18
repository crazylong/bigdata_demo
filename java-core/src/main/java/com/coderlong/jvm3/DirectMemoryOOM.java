package com.coderlong.jvm3;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * VM Args: -Xmx20m -XX:MaxDirectMemorySize=10m
 * @author LongQiong
 * @date 2021/2/25
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        int i = 0;
        while (true) {
            try {
                i++;
                unsafe.allocateMemory(_1MB);

            } catch (Throwable e) {
                System.out.println("i=" + i);
                e.printStackTrace();
            }
        }
    }
}
