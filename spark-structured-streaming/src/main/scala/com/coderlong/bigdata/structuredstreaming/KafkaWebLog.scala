package com.coderlong.bigdata.structuredstreaming

import com.coderlong.bigdata.structuredstreaming.log.LazyLogger
import org.apache.spark.sql.SparkSession

object KafkaWebLog extends LazyLogger{
  case class Weblog(datatime: String,
                    userid: String,
                    searchname: String,
                    retorder: String,
                    cliorder: String,
                    cliurl: String)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[2]")
      .appName("streaming").getOrCreate()

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.242.70:9092")
      .option("subscribe", "weblogs")
      .option("kafka.group.id", "consumer_weblog1")
      .option("startingOffsets","latest")//"latest" for streaming, "earliest" for batch
      //.option("endingOffsets", "latest")
      .load()

    import spark.implicits._
    val lines = df.selectExpr("CAST(value AS STRING)").as[String]
    val weblog = lines.map(_.split(",")).map(x => Weblog(x(0), x(1), x(2), x(3), x(4), x(5)))
    //println(weblog.collectAsList())
    val titleCount = weblog.groupBy("searchname").count().toDF("titleName", "webcount")

    val url = "jdbc:mysql://spark:3306/testdb?useSSL=false"
    val username = "root"
    val password = "123456"

    val writer = new JdbcSink(url, username, password)
    val weblogcount = titleCount.writeStream
      .foreach(writer)
      .outputMode("update")
      .start()

    weblogcount.awaitTermination()
  }

}
