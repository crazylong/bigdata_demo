package com.coderlong.phoenix.spark.scala.demo

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by hadoop on 18-2-19.
  */
object SparkReadPhoenixDF {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("testPhonix")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)


   val df = sqlContext.read.format("org.apache.phoenix.spark").options(Map("table" -> "WEB_STAT", "zkUrl" -> "spark1234:12181")).load()

    df.filter(df("HOST") === "EU").show

  }
}
