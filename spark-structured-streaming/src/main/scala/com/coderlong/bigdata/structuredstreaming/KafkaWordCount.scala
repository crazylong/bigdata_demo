package com.coderlong.bigdata.structuredstreaming

import java.util.UUID

import com.coderlong.bigdata.structuredstreaming.log.LazyLogger
import org.apache.spark.sql.SparkSession

object KafkaWordCount  extends LazyLogger{
  def main(args: Array[String]): Unit = {
    if(args.length < 3) {
      System.err.println("Usage: StructuredKafkaWordCount <bootstrap-servers> " +
        "<subscribe-type> <topics> [<checkpoint-location>]")
      System.exit(1)
    }

    val Array(bootStrapServers, subscribeType, topics, _*) = args

    val checkPointLocation =
      if(args.length > 3) args(3) else "D://tmp/temporary-" + UUID.randomUUID().toString

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("StructuredKafkaWordCount")
      .getOrCreate()

    import spark.implicits._
    //Create DataSet representing the stream of input lines from kafka
    val lines = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootStrapServers)
      .option(subscribeType, topics)
      .load()
      .selectExpr("CAST(value AS STRING)")
      .as[String]

    // Generate running word count
    val wordCounts = lines.flatMap(_.split(" ")).groupBy("value").count()

    // Start running the query that prints the running counts to the console
//    val query = wordCounts.writeStream
//      .outputMode("complete")
//      .format("console")
//      .option("checkpointLocation", checkPointLocation)
//      .start()




    val url = "jdbc:mysql://spark:3306/testdb?useSSL=false"
    val username = "root"
    val password = "123456"

    val writer = new JdbcSink(url, username, password)
    val query = wordCounts.writeStream
      .foreach(writer)
      .outputMode("update")
      .start()

//    val query = wordCounts.writeStream
//      .format("json")        // can be "orc", "json", "csv", etc.
//      .option("path", "D://test")
//      .option("checkpointLocation", "./ck1")
//      .start()

    query.awaitTermination()

  }
}
