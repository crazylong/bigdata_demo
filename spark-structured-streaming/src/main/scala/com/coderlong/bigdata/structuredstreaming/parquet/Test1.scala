package com.coderlong.bigdata.structuredstreaming.parquet

import org.apache.spark.sql.SparkSession

object Test1 {
  def main(args: Array[String]): Unit = {
    val appName = "Scala Parquet Example"
    val master = "local"

    /*Create Spark session with Hive supported.*/
    val spark = SparkSession.builder.appName(appName).master(master).getOrCreate()
    val df = spark.read.format("csv").option("header", "true").load("D:\\code\\bigdata_demo\\spark-structured-streaming\\src\\main\\resources\\people.csv")
    /*Write parquet file*/
    df.write.parquet("people.parquet")
    val df2 = spark.read.parquet("people.parquet")
    df2.show()
  }

}
