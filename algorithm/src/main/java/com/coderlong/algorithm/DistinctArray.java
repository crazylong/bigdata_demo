package com.coderlong.algorithm;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * 数组去重
 * @author Long Qiong
 * @create 2019/5/20
 */
public class DistinctArray {
    public static void main(String[] args) {
        String[] arr = {"hello", "world", "java", "world", "c#", "java", "python", "world"};
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i].equals(arr[j])){
                    arr = ArrayUtils.remove(arr, j);
                }
            }
        }
        System.out.println(StringUtils.join(arr, ","));
    }

    @Test
    public void test01(){
        String[] arr = {"hello", "world", "java", "world", "c#", "java", "python", "world"};
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {

            try {
                if((i+1)<arr.length && arr[i] == arr[i+1]){
                    arr = ArrayUtils.remove(arr, i + 1);
                    i=0;
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        System.out.println(StringUtils.join(arr, ","));
    }
}
