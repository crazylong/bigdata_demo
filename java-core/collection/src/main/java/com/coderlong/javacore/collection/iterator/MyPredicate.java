package com.coderlong.javacore.collection.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Long Qiong
 * @create 2019/3/25
 */
public class MyPredicate {
    @Test
    public void test01(){
        List<String> books = new ArrayList<>();
        books.add("java");
        books.add("scala");
        books.add("spark");
        books.add("hadoop");
        books.add("yarn");

        books.removeIf(i->i.contains("ar"));

        System.out.println(predicateTest(books, item -> item.toString().contains("ar")));
        System.out.println(predicateTest(books, item->item.toString().length()>4));
    }

    public static int predicateTest(Collection cs, Predicate p){
        int sum = 0;


        for(Object obj : cs){
            if(p.test(obj)){
                sum++;
            }
        }
        return sum;
    }
}
