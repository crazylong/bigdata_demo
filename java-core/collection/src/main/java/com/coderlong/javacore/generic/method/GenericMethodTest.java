package com.coderlong.javacore.generic.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 泛型方法
 * @author Long Qiong
 * @create 2019/3/27
 */
public class GenericMethodTest {
    static <T> void fromArrayToCollection(T[] array, Collection<T> collection){
        for (T t : array) {
            collection.add(t);
        }
    }

    public static void main(String[] args) {
        //GenericMethodTest t = new GenericMethodTest();
        Object[] array1 = new Object[5];
        Collection<Object> collection1 = new ArrayList<>();
        fromArrayToCollection(array1, collection1);
    }

    static <T> void test(List<? extends T> from, List<T> to){

    }



    static <T> void test2(List<T> from, List<T> to){

    }

    static <T extends Number> void test3(List<T> from, List<T> to){

    }

    static <T extends E, E> void test4(List<T> from, List<E> to){

    }
}
