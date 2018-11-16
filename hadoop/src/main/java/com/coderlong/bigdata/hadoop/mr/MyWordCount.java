package com.coderlong.bigdata.hadoop.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 词频统计
 * @author Long Qiong
 * @create 2018/9/5
 *
 * words
 * hello qiangfeng hello world
 * hi qianfeng hi world
 * best best best
 *
 *map阶段
 * 行偏移量：每一行第一个字母距离该文件的首位置的距离
 *
 *         map阶段输入key类型（行偏移量)      map阶段输入value类型     map阶段输出key类型      map阶段输出value类型
 * Mapper<KEYIN,                        VALUEIN,                KEYOUT,                 VALUEOUT>
 * map阶段的输入：
 * 0    hello qiangfeng hello world
 * 27   hi qianfeng hi world
 * 47   best best best
 *
 * reduce阶段的输入必须跟map阶段的输出类型相同
 */
public class MyWordCount {
    public static class MyMapper extends Mapper<IntWritable, Text, Text, IntWritable>{

        @Override
        protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);
        }
    }

    public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }
    }

    public static void main(String[] args) {

    }


}
