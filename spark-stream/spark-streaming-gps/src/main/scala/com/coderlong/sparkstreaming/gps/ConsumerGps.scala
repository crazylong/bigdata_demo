package com.coderlong.sparkstreaming.gps

import java.sql.DriverManager
import java.util.Properties
import java.util.regex.Pattern

import cn.hutool.core.date.DateUtil
import com.coderlong.sparkstreaming.util.JsonParse
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}

import scala.collection.JavaConversions._

object ConsumerGps {
  def main(args: Array[String]): Unit = {
    //region columns

    var columns = Map[String, Int]()
    columns += ("info_NO" -> 1)
    columns += ("info_area1" -> 2)
    columns += ("info_area2" -> 3)
    columns += ("call_id" -> 4)
    columns += ("info_color" -> 5)
    columns += ("info_data_type" -> 6)
    columns += ("from" -> 7)
    columns += ("info_gnsscenterid" -> 8)
    columns += ("info_gps_alarm" -> 9)
    columns += ("info_gps_altitude" -> 10)
    columns += ("info_gps_cmb_date" -> 11)
    columns += ("info_gps_direction" -> 12)
    columns += ("info_gps_excrypt" -> 13)
    columns += ("info_gps_lat" -> 14)
    columns += ("info_gps_lon" -> 15)
    columns += ("info_gps_state" -> 16)
    columns += ("info_gps_time" -> 17)
    columns += ("info_gps_vec1" -> 18)
    columns += ("info_gps_vec2" -> 19)
    columns += ("info_gps_vec3" -> 20)
    columns += ("method" -> 21)
    columns += ("to" -> 22)
    columns += ("type" -> 23)
    columns += ("info_type" -> 24)
    //endregion

    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("SparkStreamingOnKafkaDirect")
    //设置每个分区每秒读取多少条数据
    //conf.set("spark.streaming.kafka.maxRatePerPartition","3")
    val ssc = new StreamingContext(conf,Durations.seconds(5))
    //设置日志级别
    //ssc.sparkContext.setLogLevel("Error")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "hadoop01:9092,hadoop02:9092,hadoop03:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "MyGroupId2",//

      /**
        * 当没有初始的offset，或者当前的offset不存在，如何处理数据
        *  earliest ：自动重置偏移量为最小偏移量
        *  latest：自动重置偏移量为最大偏移量【默认】
        *  none:没有找到以前的offset,抛出异常
        */
      "auto.offset.reset" -> "latest",

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

    val transStrem: DStream[String] = kafkaStream.map(record => {
      val key_value = (record.key, record.value)
      println("receive message key = "+key_value._1)
      println("receive message value = "+key_value._2)
      key_value._2
    })
    val wordsDS: DStream[String] = transStrem.flatMap(line=>{line.split(" ")})
    val result: DStream[(String, Int)] = wordsDS.map((_,1)).reduceByKey(_+_)
    result.print()
    val kafkaRDD : DStream[String] = null
//        val kafkaRDD: DStream[String] = kafkaStream.map(record => {
//          record.value
//        })

    val connProp = new Properties
    connProp.put("driver", "org.apache.phoenix.jdbc.PhoenixDriver")
    connProp.setProperty("phoenix.functions.allowUserDefinedFunctions", "true") //自定义函数
    connProp.setProperty("phoenix.mutate.batchSize", "15000000") //执行过程被批处理的最大行数
    connProp.setProperty("phoenix.mutate.maxSize", "2000000") //客户端批处理的最大行数
    connProp.setProperty("phoenix.mutate.maxSizeBytes", "1048576000") //客户端批处理的最大数据量 1g

//    val kafkaRDD: DStream[String] = kafkaStream.map(record => {
//      record.value
//    })
//    //val kafkaRDD = kafkaStream.map(_._2)
//    kafkaRDD.print()
//    println("===================================================")
//    kafkaRDD.foreachRDD(rdd=>{
//      println(rdd)
//      //rdd.print()
//    })

    if(1==1){
      return
    }

    //      .persist(StorageLevel.MEMORY_AND_DISK_SER)
    //增加分区 repartition
   /* kafkaRDD.repartition(100).foreachRDD(rdd => {
      try{
        rdd.foreachPartition(row => {
          println("=====================start rdd foreachPartition")
          Class.forName("org.apache.phoenix.jdbc.PhoenixDriver")
          val conn = DriverManager.getConnection("jdbc:phoenix:hadoop01,hadoop02,hadoop03:2181",connProp)
          val stmt = conn.prepareStatement("UPSERT INTO NBGGPSINFO VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
          //val stmt2 = conn.prepareStatement(phoenixSQL2);
          conn.setAutoCommit(false)

          println("==========row size=" + row.size)
          row.foreach(line => {
            println("=====================start row foreach")
            println("=====================line:" + line)
            //车牌正则的校验
            val pattern = """[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣贛鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}"""
            if (line.contains("info")){

              var outStrVehicleNo:String = ""
              val allStr: String = JsonParse.upColumnToKeepKey(line, "info")
              if (allStr.contains("info_gps")){
                val str2 = JsonParse.upColumnToKeepKey(allStr, "info_gps")
                val map = JsonParse.parseJson(str2)
                if (map.keySet().contains("info_area1")
                  && map.keySet().contains("info_area2")
                  && map.getOrElse("info_NO","").toString != null
                  && map.getOrElse("info_NO","").toString != "") {
                  for (m <- map) {
                    if ("info_NO".equals(m._1)) {
                      val p = Pattern.compile(pattern)
                      val matcher = p.matcher(m._2.toString)
                      while (matcher.find()){
                        outStrVehicleNo += matcher.group()
                      }
                      stmt.setString(1, outStrVehicleNo)
                      //stmt2.setString(1, outStrVehicleNo)
                      stmt.setString(26, outStrVehicleNo)
                      //stmt2.setString(26, outStrVehicleNo)
                    } else if ("info_gps_time".equals(m._1)) {
                      stmt.setString(17, m._2.toString)
                      //stmt2.setString(17, m._2.toString)
                      stmt.setString(27, m._2.toString)
                      //stmt2.setString(27, m._2.toString)
                    } else {
                      stmt.setString(columns.getOrElse(m._1, "").toString.toInt, m._2.toString)
                      //stmt2.setString(columns.getOrElse(m._1, "").toString.toInt, m._2.toString)
                    }
                  }
                  stmt.setString(25, "")
                  //stmt2.setString(25, "")
                  //insert_time
                  val dateNow = DateUtil.now()
                  stmt.setString(28, dateNow)
                  //stmt2.setString(28, dateNow)
                  stmt.addBatch()
                 // stmt2.addBatch()
                } else if (map.size() == 22 && map.getOrElse("info_NO","").toString != null && map.getOrElse("info_NO","").toString != ""){
                  for (m <- map) {
                    if ("info_NO".equals(m._1)) {
                      val p = Pattern.compile(pattern)
                      val matcher = p.matcher(m._2.toString)
                      while (matcher.find()){
                        outStrVehicleNo += matcher.group()
                      }
                      stmt.setString(1, outStrVehicleNo)
                      //stmt2.setString(1, CAR_NO.toString)
                      stmt.setString(26, outStrVehicleNo)
                      //stmt2.setString(1, outStrVehicleNo.toString)
                      //stmt2.setString(26, outStrVehicleNo.toString)
                    } else if ("info_gps_time".equals(m._1)) {
                      stmt.setString(17, m._2.toString)
                      stmt.setString(27, m._2.toString)
                      //stmt2.setString(17, m._2.toString)
                     // stmt2.setString(27, m._2.toString)
                    } else {
                      stmt.setString(columns.getOrElse(m._1, "").toString.toInt, m._2.toString)
                     // stmt2.setString(columns.getOrElse(m._1, "").toString.toInt, m._2.toString)
                    }
                  }
                  stmt.setString(2, "")
                  //stmt2.setString(2, "")
                  stmt.setString(3, "")
                  //stmt2.setString(3, "")
                  stmt.setString(25, "")
                  //stmt2.setString(25, "")
                  //insert_time
                  val dateNow = DateUtil.now()
                  stmt.setString(28, dateNow)
                  //stmt2.setString(28, dateNow)
                  stmt.addBatch()
                 // stmt2.addBatch()
                }
              }
            }
          })
          stmt.executeBatch()
          //stmt2.executeBatch()
          println("插入完毕")
          conn.commit()
          conn.close()
        })
      }catch {
        case e => e.printStackTrace()
      }

    })*/


    /**
      * 以上业务处理完成之后，异步的提交消费者offset,这里将 enable.auto.commit 设置成false,就是使用kafka 自己来管理消费者offset
      * 注意这里，获取 offsetRanges: Array[OffsetRange] 每一批次topic 中的offset时，必须从 源头读取过来的 stream中获取，不能从经过stream转换之后的DStream中获取。
      */
    kafkaStream.foreachRDD { rdd =>
      val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      // some time later, after outputs have completed
      kafkaStream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    }
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
