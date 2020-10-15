package com.coderlong.bigdata.scala.spark.sql

import org.apache.spark.sql.SparkSession

object SparkUtil {
  def getSparkSession: SparkSession = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("Spark SQL basic example")
      .getOrCreate()
    spark.sparkContext.setLogLevel("warn")
    spark
  }
}
