package com.coderlong.bigdata.scala.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
/**
 * @author Long Qiong
 * @create 2018/9/17
 */
object WordCount {
  def main(args: Array[String]): Unit = {
//    System.setProperty("HADOOP_USER_NAME", "root")
//    System.setProperty("SPARK_YARN_MODE", "yarn")
    //1.
    //val conf = new SparkConf().setAppName("wordCount").setMaster("local[*]")

//    //2.
    val conf = new SparkConf()
      .setAppName("myWordCount")
      .setMaster("yarn")
      //.setMaster("yarn-client")
      //.setJars(Array("E:\\books\\大数据\\bigdata_demo\\spark\\target\\spark-1.0-SNAPSHOT.jar"))
      //.setIfMissing("spark.driver.host", "192.168.153.161")
      .set("deploy-mode", "client")
      //.set("spark.sql.hive.metastore.jars", "builtin")

    //3.采用submit提交
    //
//    var conf = new SparkConf()
//      .setAppName("wordCount")
//      .setIfMissing("spark.driver.host", "172.18.203.131")


        //4.
//        val conf = new SparkConf()
//          .setAppName("myWordCount")
//          .set("spark.master", "yarn-client")
//          .set("spark.driver.host","cdh02-01")
//          .set("spark.sql.hive.metastore.jars", "builtin")
//          .setJars(Array("E:\\books\\大数据\\bigdata_demo\\spark\\target\\spark-1.0-SNAPSHOT.jar"))
//          .setSparkHome("D:\\spark\\spark-cdh6")
//          .set("SPARK_LOCAL_IP=127.0.0.1 ", "")
////          .setIfMissing("spark.driver.host", "192.168.153.161")
////          .set("deploy-mode", "client")
////          .set("spark.sql.hive.metastore.jars", "builtin")

      //5.
      //val conf = new SparkConf().setAppName("wordCount").setMaster("local[*]")

    val sc = new SparkContext(conf)
    //本地调试
    //val file: RDD[String] = sc.textFile("hdfs://cdh02-05:8020/user/admin/test.txt")

    //采用submit提交
    val file: RDD[String] = sc.textFile("hdfs://nameservice1/test.txt")
    val words: RDD[String] = file.flatMap(_.split(" "))
    val tuple:RDD[(String, Int)] = words.map((_,1))
    val result: RDD[(String, Int)] = tuple.reduceByKey(_+_)
    val resultSort: RDD[(String, Int)] = result.sortBy(_._2, false)
    result.collect()
    println(result.collect())
    resultSort.foreach(println)

//    var ss :SparkSession = new SparkSession(sc);
//    ss.sql("SELECT date_sub(now(), 2)").show;
//    ss.stop()


    //本地调试
    //result.saveAsTextFile("hdfs://cdh02-05:8020/user/admin/out")

    //submit提交
    //result.saveAsTextFile("hdfs://nameservice1/user/admin/out")


    sc.stop()
  }
/*  def main(args: Array[String]){
    val conf = new SparkConf().setAppName("wordCount").setMaster("yarn-client")
    val sc = new SparkContext(conf)

    val file: RDD[String] = sc.textFile("hdfs://nameservice1/user/admin/test.txt")

    val words: RDD[String] = file.flatMap(_.split(" "))
    val tuple:RDD[(String, Int)] = words.map((_,1))
    val result: RDD[(String, Int)] = tuple.reduceByKey(_+_)
    val resultSort: RDD[(String, Int)] = result.sortBy(_._2, false)
    result.collect()
    println(result.collect())
    resultSort.foreach(println)
  }*/
}
