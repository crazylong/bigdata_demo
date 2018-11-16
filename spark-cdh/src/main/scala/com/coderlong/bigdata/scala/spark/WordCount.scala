package com.coderlong.bigdata.scala.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.conf.Configuration
import org.apache.spark.deploy.yarn.YarnSparkHadoopUtil
import org.apache.spark.deploy.{Client, SparkHadoopUtil}
import org.apache.spark.mapred.SparkHadoopMapRedUtil
/**
 * @author Long Qiong
 * @create 2018/9/17
 */
object WordCount {
  def main(args: Array[String]): Unit = {
//    System.setProperty("HADOOP_USER_NAME", "root")
//    System.setProperty("SPARK_YARN_MODE", "yarn")
      //SPARK_HOME=D:\spark-cdh6\spark
      //System.setProperty("SPARK_HOME", "D:\\spark-cdh6\\spark")
      //(System.getenv()).forEach(x => {println(x)})
//      println(System.getenv("SPARK_HOME"))
//      println(System.getenv("JAVA_HOME"))
//      return

      //var map = Map[String,String]("name" -> "jason","age" -> "500","test_100" -> "test_100","test_101" -> "test_101")
//map.foreach(m=>println(m));
    //1.
    //val conf = new SparkConf().setAppName("wordCount").setMaster("local[*]")

//    //2.
//      val confg = new Configuration()
//      confg.addResource("")
    var configuration : org.apache.hadoop.conf.Configuration = new Configuration()
      //YarnSparkHadoopUtil
    //SparkHadoopUtil
    //SparkHadoopMapRedUtil
    val conf = new SparkConf()
      .setAppName("myWordCount")
      .setMaster("yarn")
      //.setExecutorEnv("SPARK_HOME", "D:\\spark-cdh6\\spark")
      //.setMaster("yarn-client")
      .setJars(Array("E:\\books\\大数据\\bigdata_demo\\spark-cdh\\target\\spark-cdh-1.0-SNAPSHOT.jar"))
      //.setIfMissing("spark.driver.host", "192.168.153.161")
      .set("deploy-mode", "client")
    //conf.setAll(configuration.get)
      //.set("SPARK_LOCAL_IP")
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

    val sc = new SparkContext(conf)


    //本地调试
    val file: RDD[String] = sc.textFile("hdfs://cdh02-05:8020/user/admin/test.txt")

    //采用submit提交
    //val file: RDD[String] = sc.textFile("hdfs://nameservice1/user/admin/test.txt")
    val words: RDD[String] = file.flatMap(_.split(" "))
    val tuple:RDD[(String, Int)] = words.map((_,1))
    val result: RDD[(String, Int)] = tuple.reduceByKey(_+_)
    val resultSort: RDD[(String, Int)] = result.sortBy(_._2, false)
    result.collect()
    println(result.collect())
      resultSort.collect()
    resultSort.foreach(println)

    //本地调试
    //result.saveAsTextFile("hdfs://cdh02-05:8020/user/admin/out")

    //submit提交
    //result.saveAsTextFile("hdfs://nameservice1/user/admin/out2")


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
