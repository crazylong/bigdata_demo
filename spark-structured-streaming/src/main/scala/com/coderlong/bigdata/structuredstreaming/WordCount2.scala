package com.coderlong.bigdata.structuredstreaming

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

import org.apache.log4j.Logger
import org.apache.log4j.Level

object WordCount2 {

  def main(args: Array[String]): Unit = {
    //hadoop-2.6.5至hadoop-3.2.1的winutils.exe见：https://github.com/cdarlint/winutils，若暂时不宜重启电脑，可代码中设置：
    //System.setProperty("hadoop.home.dir", "C:\\hadoop\\hadoop-3.1.4\\")
    //Logger.getLogger("org").setLevel(Level.OFF)
    //System.setProperty("spark.ui.showConsoleProgress","false")

    //SetNoLogger

    val spark = SparkSession
      .builder
      .appName("StructuredNetworkWordCount")
      .master("local[2]")
      .getOrCreate()

    //spark.sparkContext.setLogLevel("error")

    import spark.implicits._

    // Create DataFrame representing the stream of input lines from connection to localhost:9999
    val lines = spark.readStream
      .format("socket")
      .option("host", "hadoop03")
      .option("port", 9999)
      .load()

    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("value").count()

  //print(wordCounts.collect())



    // Start running the query that prints the running counts to the console
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()


    query.awaitTermination()
  }

  def SetNoLogger={
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("com").setLevel(Level.OFF)
    System.setProperty("Spark.ui.showConsoleProgress","false")
    Logger.getRootLogger().setLevel(Level.OFF)
  }

}
