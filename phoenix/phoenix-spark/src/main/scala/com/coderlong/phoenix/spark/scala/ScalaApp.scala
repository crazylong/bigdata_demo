package com.coderlong.phoenix.spark.scala

import java.util.Properties

import org.apache.hadoop.conf.Configuration
import org.apache.phoenix.spark._
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import scala.util.Properties

object ScalaApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("phoenix-test")
      .getOrCreate()



    // 第一种读取方法
//    var df = spark.read.format("org.apache.phoenix.spark")
//      .option("table", "lq.events")
//      .option("zkUrl", "hadoop01:2181")
//      .option("phoenix.schema.isNamespaceMappingEnabled", "true")
//      .load()

    val prop : Properties = new Properties()
    prop.put("phoenix.schema.isNamespaceMappingEnabled", "true")
    var df = spark.read.format("org.apache.phoenix.spark")
      .jdbc("jdbc:phoenix:hadoop01,hadoop02,hadoop03", "lq.events", prop)

    df = df.filter("date >= '2019-09-20'")
      .filter("date < '2019-09-24'")
        .select("TIME", "DATE")
    df.show()
    //df.select("time, date").show()

    //var df2: DataFrame = df.select("TIME", "DATE")
    //df2.show()
    //df2.ph
    //第二种输出方法

    //2.1
    //需要配置hbase-site.xml，否则提示
    //Cannot initiate connection as SYSTEM:CATALOG is found but client does not have phoenix.schema.isNamespaceMappingEnabled enabled
    df.saveToPhoenix(Map("table" -> "lq.events_tmp", "zkUrl" -> "hadoop01:2181"))

    //2.2 无效
//    val conf : Configuration = new Configuration()
//    conf.set("phoenix.schema.isNamespaceMappingEnabled", "true")
//    df.saveToPhoenix("lq.events_tmp",conf, Option.apply("hadoop01:2181"))


    //2.3 sql解析异常
//    df.write.format("org.apache.phoenix.spark")
//      .mode(SaveMode.Append)
//      .jdbc("jdbc:phoenix:hadoop01,hadoop02,hadoop03", "lq.events_tmp", prop)

  }
}
