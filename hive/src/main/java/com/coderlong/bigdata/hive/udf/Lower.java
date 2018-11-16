package com.coderlong.bigdata.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 用户自定义UDF函数Lower
 * @author Long Qiong
 * @create 2018/9/10
 */
public class Lower extends UDF {
    public String evaluate(String s){
        if(null != s){
            return s.toLowerCase();
        }
        return null;
    }
}
