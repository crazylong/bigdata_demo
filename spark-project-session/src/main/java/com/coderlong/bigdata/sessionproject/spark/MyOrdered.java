package com.coderlong.bigdata.sessionproject.spark;

import scala.math.Ordered;

import java.io.Serializable;

/**
 * @author Long Qiong
 * @create 2019/5/12
 */
public class MyOrdered implements Ordered<CategorySortKey>, Serializable {
    @Override
    public int compare(CategorySortKey that) {
        return 0;
    }

    @Override
    public boolean $less(CategorySortKey that) {
        return false;
    }

    @Override
    public boolean $greater(CategorySortKey that) {
        return false;
    }

    @Override
    public boolean $less$eq(CategorySortKey that) {
        return false;
    }

    @Override
    public boolean $greater$eq(CategorySortKey that) {
        return false;
    }

    @Override
    public int compareTo(CategorySortKey that) {
        return 0;
    }
}
