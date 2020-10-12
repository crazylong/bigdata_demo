package com.coderlong.util;

import org.junit.Test;

import java.math.BigInteger;
import java.util.BitSet;

public class BitTest {
    @Test
    public void test01(){
        System.out.println(1 << 1);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
    }

    @Test
    public void test02(){
        String binary = "00000001";
        BitSet bitSet = new BitSet();
        for (int i = 0; i < binary.length(); i++) {
            String c = String.valueOf(binary.charAt(i));
            if ("1".equals(c)) {
                bitSet.set(i, true);
            }
        }

        System.out.println(bitSet);
    }

    @Test
    public void test03(){
        String b1 = "00000001";
        BigInteger bi = new BigInteger(b1, 2);    //转换为BigInteger类型
//         return Integer.parseInt(bi.toString());     //转换成十进制
        System.out.println(bi);
        System.out.println(new BigInteger("00000010", 2));
        System.out.println(new BigInteger("00000100", 2));
        System.out.println(new BigInteger("00001000", 2));
        System.out.println(new BigInteger("0000010000000000", 2));
        System.out.println(new BigInteger("00000100000000000000000000000000", 2));
    }

    @Test
    public void test04(){
        System.out.println(10000*Math.pow(1.1865,40));
    }
}
