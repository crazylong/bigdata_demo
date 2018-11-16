package com.coderlong.bigdata.java.sparkcdh;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Long Qiong
 * @create 2018/11/14
 */
public class WorkCount3 {
    /**
     * spark 测试
     */
    public static void main(String[] args){
        System.setProperty("HADOOP_USER_NAME", "admin");
        //通过jar包发布才能获取
        String[] jars = JavaSparkContext.jarOfClass(WorkCount3.class);
        for(String s : jars){
            System.out.println(s);
        }

        List<String> jarList = new ArrayList<>();
        jarList.addAll(Arrays.asList(jars));

        try {
            //用于本地调试
            jarList.add("E:\\books\\大数据\\bigdata_demo\\spark-cdh\\target\\spark-cdh-1.0-SNAPSHOT.jar");
        } catch (Exception e){
            e.printStackTrace();
        }

        SparkConf sparkConf = new SparkConf()
                .setAppName("profile-spark2")
                .setMaster("yarn")
                .set("deploy-mode", "client")
                .set("executor-memory", "2g")
                .set("total-executor-cores", "1")
                //.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                //.set("spark.serializer", "org.apache.spark.serializer.JavaSerializer")
                //.setJars(new String[]{"E:\\books\\大数据\\bigdata_demo\\spark-cdh\\target\\spark-cdh-1.0-SNAPSHOT.jar"})
                .setJars(jarList.toArray(new String[0]))
                //.registerKryoClasses(new Class[]{ WorkCount3.class})
                ;

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        if(javaSparkContext == null){
            return;
        }
        JavaRDD<String> lines = javaSparkContext.textFile("hdfs://cdh02-05:8020/user/admin/test.txt");

        JavaRDD<String> words = lines.flatMap((s1) -> Arrays.asList(s1.split(" ")).iterator());




        JavaPairRDD<String, Integer> ones = words.mapToPair(i-> new Tuple2<>(i, 1));


        JavaPairRDD<String, Integer> wordCounts = ones.reduceByKey((i, j) -> i + j);


        List<Tuple2<String, Integer>> output = wordCounts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
    }
}
