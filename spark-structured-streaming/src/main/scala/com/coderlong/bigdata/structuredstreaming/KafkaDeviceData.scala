package com.coderlong.bigdata.structuredstreaming

import java.sql.{Connection, ResultSet, Statement}
import java.util.{Date, UUID}

import com.coderlong.util.DateUtil
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.streaming.{OutputMode, StreamingQueryListener}
import org.apache.spark.sql.streaming.StreamingQueryListener.{QueryProgressEvent, QueryStartedEvent, QueryTerminatedEvent}
import org.apache.spark.sql.{Column, Dataset, SparkSession}
import org.apache.spark.sql.types.{DateType, DoubleType, LongType, StringType, StructType, TimestampType}

object KafkaDeviceData {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Structured_Device_Data")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    spark.streams.addListener(new StreamingQueryListener() {

      override def onQueryProgress(queryProgress: QueryProgressEvent): Unit = {
        //print(DateUtil.getTime)
        //queryProgress.progress.
        println("Query made progress: " + queryProgress.progress)
      }

      override def onQueryStarted(event: QueryStartedEvent): Unit = {

      }

      override def onQueryTerminated(event: QueryTerminatedEvent): Unit = {

      }
    })

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.242.70:9092,192.168.242.71:9092,192.168.242.72:9092")
      .option("subscribe", "device_data_topic")
      .option("startingOffsets","earliest")//"latest" for streaming, "earliest" for batch
      .load()

    df.printSchema()
    import spark.implicits._

    df.select(from_json(col("value").cast(StringType), DeviceDataSchema)).printSchema()

    val ds: Dataset[DeviceData] = df.select(from_json(col("value").cast(StringType), DeviceDataSchema).alias("device_json"))
      .select("device_json.*")
      .as[DeviceData]

    ds.printSchema()

    val checkpointLocation = "D://tmp/checkpoint/deviceData"

    ds.writeStream
      .format("console")
      .outputMode(OutputMode.Update())
      //.option("checkpointLocation", checkpointLocation)
      .start()
      .awaitTermination()
  }

  case class DeviceData(device: String, deviceType: String, signal: String, time: Long)

  val DeviceDataSchema = new StructType()
    .add("device", StringType)
    .add("deviceType", StringType)
    .add("signal", StringType)
    .add("time", LongType)

}
