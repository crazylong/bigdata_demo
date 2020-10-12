package com.coderlong.bigdata.structuredstreaming

import org.apache.spark.sql.SparkSession

object WordCount {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder().master("...").getOrCreate()  // 创建一个 SparkSession 程序入口
//
//    val lines = spark.readStream.textFile("some_dir")  // 将 some_dir 里的内容创建为 Dataset/DataFrame；即 input table
//    val words = lines.flatMap(_.split(" "))
//
//    val wordCounts = words.groupBy("value").count()    // 对 "value" 列做 count，得到多行二列的 Dataset/DataFrame；即 result table
//
//    val query = wordCounts.writeStream                 // 打算写出 wordCounts 这个 Dataset/DataFrame
//      .outputMode("complete")                          // 打算写出 wordCounts 的全量数据
//      .format("console")                               // 打算写出到控制台
//      .start()                                         // 新起一个线程开始真正不停写出
//
//    query.awaitTermination()                           // 当前用户主线程挂住，等待新起来的写出线程结束
//  }
}
