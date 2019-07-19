package com.coderlong.bigdata.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, StreamingContext}

object WindowOperator {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("windowOperatorTest")
    val ssc = new StreamingContext(conf, Durations.seconds(5))
    ssc.sparkContext.setLogLevel("ERROR")
    val sourceStream: ReceiverInputDStream[String] = ssc.socketTextStream("spark", 9999)
    val flatMapStream: DStream[String] = sourceStream.flatMap(line => {
      line.split(" ")
    })
    val wordMapStream: DStream[(String, Int)] = flatMapStream.map(word => {
      (word, 1)
    })

    //1.普通模式
    //每5秒统计前15秒的数据
    //    wordMapStream.reduceByKeyAndWindow(
    //      (i1:Int, i2:Int) => {i1 + i2},
    //      Durations.seconds(15),
    //      Durations.seconds(5))
    //  }

    //2.优化模式
    ssc.checkpoint("./data/SparkStreaming")
    val reduceStream: DStream[(String, Int)] = wordMapStream.reduceByKeyAndWindow(
      (i1: Int, i2: Int) => {
        i1 + i2
      }, //加新批次的数据
      (i1: Int, i2: Int) => {
        i1 - i2
      }, //减旧批次的数据
      Durations.seconds(15),
      Durations.seconds(5)
    )
    reduceStream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
