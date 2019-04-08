package com.coderlong.javacore.generic.upper.apple;

import java.io.Serializable;

/**
 * @author Long Qiong
 * @create 2019/3/27
 */
public class AppleTest<T extends AppleParent & Serializable> {
    public static void main(String[] args) {
        //AppleTest<AppleSub> a1 = new AppleTest<>();
        AppleTest<AppleSub2> a2 = new AppleTest<>();
    }
}
