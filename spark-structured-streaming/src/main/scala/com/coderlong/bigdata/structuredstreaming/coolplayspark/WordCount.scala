package com.coderlong.bigdata.structuredstreaming.coolplayspark

import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    val lines = spark.readStream.option("basePath", "D:\\code\\bigdata_demo\\spark-structured-streaming\\src\\main\\resources").textFile("D:\\code\\bigdata_demo\\spark-structured-streaming\\src\\main\\resources\\helloword.txt")

    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.groupBy("value").count()
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
