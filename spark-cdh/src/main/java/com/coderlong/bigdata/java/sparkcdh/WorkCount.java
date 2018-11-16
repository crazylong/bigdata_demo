package com.coderlong.bigdata.java.sparkcdh;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Serializable;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Long Qiong
 * @create 2018/11/14
 */
public class WorkCount{
    /**
     * spark 测试
     */
    public static void main(String[] args){
        System.setProperty("HADOOP_USER_NAME", "admin");
        //通过jar包发布才能获取
        String[] jars = JavaSparkContext.jarOfClass(WorkCount.class);
        for(String s : jars){
            System.out.println(s);
        }

        List<String> jarList = Arrays.asList(jars);

        //用于本地调试
        jarList.add("E:\\books\\大数据\\bigdata_demo\\spark-cdh\\target\\spark-cdh-1.0-SNAPSHOT.jar");

        SparkConf sparkConf = new SparkConf()
                .setAppName("profile-spark2")
                .setMaster("yarn")
                .set("deploy-mode", "client")
                .set("executor-memory", "2g")
                .set("total-executor-cores", "1")
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                //.setJars(new String[]{"E:\\books\\大数据\\bigdata_demo\\spark-cdh\\target\\spark-cdh-1.0-SNAPSHOT.jar"})
                .setJars(jarList.toArray(new String[0]))
                //.setJars()
                .registerKryoClasses(new Class[]{WorkCount.class})
                ;
        //sparkConf.registerKryoClasses(new Class[]{WorkCount.class});
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        if(javaSparkContext == null){
            return;
        }
        JavaRDD<String> lines = javaSparkContext.textFile("hdfs://cdh02-05:8020/user/admin/test.txt");

        //JavaRDD<String> words = lines.flatMap((s1) -> Arrays.asList(s1.split(" ")).iterator());

        // 第四步：对初始的RDD进行transformation操作，也就是一些计算操作
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<String> call(String line) throws Exception {

                return Arrays.asList(line.split(" ")).iterator();

            }
        });


        //JavaPairRDD<String, Integer> ones = words.mapToPair(i-> new Tuple2<>(i, 1));
        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        //JavaPairRDD<String, Integer> counts = ones.reduceByKey((i, j) -> i + j);

       /* JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        },2);*/

        JavaPairRDD<String, Integer> wordCounts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {

                return v1 + v2;

            }
        });
//        wordCounts.foreach(new VoidFunction<Tuple2<String,Integer>>() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public void call(Tuple2<String, Integer> wordCount) throws Exception {
//                System.out.println(wordCount._1 + "------" + wordCount._2+"times.");
//            }
//        });




        List<Tuple2<String, Integer>> output = wordCounts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
    }
}
