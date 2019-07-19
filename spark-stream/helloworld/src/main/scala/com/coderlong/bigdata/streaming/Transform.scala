package com.coderlong.bigdata.streaming

import org.apache.spark.SparkConf
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
  * transform算子演示过滤黑名单
  */
object Transform {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("transformBlackList")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("ERROR")

    /**
      * 广播黑名单
      */
    val blackList: Broadcast[List[String]] = ssc.sparkContext.broadcast(List[String]("zhangsan", "lisi"))
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("spark", 9999)


    /**
      * 从实时数据中发现 数据的第二位是黑名单人员，过滤掉
      */
    val pairLines: DStream[(String, String)] = lines.map(line=>{(line.split(" ")(1), line)})

    /**
      * transform 中获取RDD，转换RDD，然后将RDD返回封装成一个新的DStream，与foreachRDD区别就是transform有返回值
      * transform内，获取RDD外的代码在driver端执行，可以使用这个特性做到动态的改变广播变量
      */
    val resultDStream: DStream[String] = pairLines.transform(pairRDD => {
      val filterRDD: RDD[(String, String)] = pairRDD.filter(tp => {
        val nameList: List[String] = blackList.value
        !nameList.contains(tp._1)
      })
      val resultRDD: RDD[String] = filterRDD.map(tp => tp._2)
      resultRDD
    })
    resultDStream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
