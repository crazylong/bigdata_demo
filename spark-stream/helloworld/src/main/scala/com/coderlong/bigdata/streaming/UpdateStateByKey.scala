package com.coderlong.bigdata.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UpdateStateByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("updateStateByKeyTest")
    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.sparkContext.setLogLevel("ERROR")
    val socketStream: ReceiverInputDStream[String] = ssc.socketTextStream("spark", 9999)
    val flatMapRDD: DStream[String] = socketStream.flatMap(line=>{line.split(" ")})
    val mapRDD: DStream[(String, Int)] = flatMapRDD.map(word=>{(word, 1)})

    ssc.checkpoint("./data/wordCountStream")

    val result: DStream[(String, Int)] = mapRDD.updateStateByKey((currentValues: Seq[Int], preValue: Option[Int]) => {
      var totalValue = 0
      if (!preValue.isEmpty) {
        totalValue += preValue.get
      }

      for (value <- currentValues) {
        totalValue += value
      }

      Option(totalValue)

    })
    result.print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }
}
