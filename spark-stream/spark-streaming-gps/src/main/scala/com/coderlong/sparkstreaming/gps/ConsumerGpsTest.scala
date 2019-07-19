package com.coderlong.sparkstreaming.gps

import java.sql.{DriverManager, PreparedStatement}
import java.util
import java.util.Properties
import java.util.regex.Pattern

import cn.hutool.core.date.DateUtil
import com.alibaba.fastjson.JSONObject
import com.coderlong.sparkstreaming.util.JsonParse
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Durations, StreamingContext}

import scala.collection.JavaConversions._
object ConsumerGpsTest {
  def main(args: Array[String]): Unit = {
    //region columns

    var columns = Map[String, Int]()
    columns += ("NO" -> 1)
    columns += ("area1" -> 2)
    columns += ("area2" -> 3)
    columns += ("color" -> 4)
    columns += ("data_type" -> 5)
    columns += ("gnsscenterid" -> 6)
    columns += ("alarm" -> 7)
    columns += ("altitude" -> 8)
    columns += ("cmb_date" -> 9)
    columns += ("direction" -> 10)
    columns += ("excrypt" -> 11)
    columns += ("lat" -> 12)
    columns += ("lon" -> 13)
    columns += ("state" -> 14)
    columns += ("time" -> 15)
    columns += ("vec1" -> 16)
    columns += ("vec2" -> 17)
    columns += ("vec3" -> 18)
    columns += ("type" -> 19)
    //endregion

    val conf = new SparkConf()
    conf.setMaster("local[4]")
    conf.setAppName("SparkStreamingOnKafkaDirect")
    //设置每个分区每秒读取多少条数据
    conf.set("spark.streaming.kafka.maxRatePerPartition","50")
    val ssc = new StreamingContext(conf,Durations.seconds(10))
    //设置日志级别
    ssc.sparkContext.setLogLevel("Error")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "hadoop01:9092,hadoop02:9092,hadoop03:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "MyGroupId3",//

      /**
        * 当没有初始的offset，或者当前的offset不存在，如何处理数据
        *  earliest ：自动重置偏移量为最小偏移量
        *  latest：自动重置偏移量为最大偏移量【默认】
        *  none:没有找到以前的offset,抛出异常
        */
      "auto.offset.reset" -> "earliest",

      /**
        * 当设置 enable.auto.commit为false时，不会自动向kafka中保存消费者offset.需要异步的处理完数据之后手动提交
        */
      "enable.auto.commit" -> (false: java.lang.Boolean)//默认是true
    )

    val topics = Array("gpsTest")
    val kafkaStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,//
      Subscribe[String, String](topics, kafkaParams)
    )

    val kafkaRDD: DStream[String] = kafkaStream.map(record => {
      record.value
    })

    val connProp = new Properties
    connProp.put("driver", "oorg.apache.phoenix.queryserver.client.Driver")
    connProp.setProperty("phoenix.functions.allowUserDefinedFunctions", "true") //自定义函数
    connProp.setProperty("phoenix.mutate.batchSize", "15000000") //执行过程被批处理的最大行数
    connProp.setProperty("phoenix.mutate.maxSize", "2000000") //客户端批处理的最大行数
    connProp.setProperty("phoenix.mutate.maxSizeBytes", "1048576000") //客户端批处理的最大数据量 1g

    try {
      kafkaRDD.foreachRDD(rdd => {
        rdd.foreachPartition(par => {

          Class.forName("org.apache.phoenix.queryserver.client.Driver")
          val conn = DriverManager.getConnection("jdbc:phoenix:thin:url=http://hadoop01:8765;serialization=PROTOBUF;", connProp)

          conn.setAutoCommit(false)
          var stmt: PreparedStatement = conn.prepareStatement("UPSERT INTO NBGGPSINFO2 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
          par.foreach(line => {
            val mapAll = JsonParse.parseJson(line)
            val mapInfo = mapAll.get("info").asInstanceOf[JSONObject]

            for (key <- mapInfo.keySet) {
              if ("gps".equals(key)) {
                val gpsInfo = mapInfo.get(key).asInstanceOf[JSONObject]
                for (gps <- gpsInfo.keySet) {
                  stmt.setString(columns.getOrElse(gps, "").toString.toInt, gpsInfo(gps).toString)
                }
              } else {
                stmt.setString(columns.getOrElse(key, "").toString.toInt, mapInfo(key).toString)
              }
            }
            stmt.addBatch()
          })

          stmt.executeBatch()
          conn.commit()
          conn.close()
          println("========保存成功=============")
        })
      })
    } catch {
      case e => e.printStackTrace()
    }
    /**
      * 以上业务处理完成之后，异步的提交消费者offset,这里将 enable.auto.commit 设置成false,就是使用kafka 自己来管理消费者offset
      * 注意这里，获取 offsetRanges: Array[OffsetRange] 每一批次topic 中的offset时，必须从 源头读取过来的 stream中获取，不能从经过stream转换之后的DStream中获取。
      */
    kafkaStream.foreachRDD { rdd =>
      val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      // some time later, after outputs have completed
//     offsetRanges.foreach(i=>{
//       println(i.partition + i.fromOffset + i.topic + i)
//     })
      kafkaStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
