package com.coderlong.bigdata.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
/**
  *
  * @author Long Qiong 
  * @create 2019/3/18
  */
object HelloWorld extends  App{
  // 创建配置
  val sparkConf = new SparkConf().setAppName("streaming").setMaster("local[*]")

  // 创建StreamingContext
  val ssc = new StreamingContext(sparkConf, Seconds(5))

  // 从Socket接收数据
  val lineDStream = ssc.socketTextStream("cdh02-01", 9111)

  val wordDStream = lineDStream.flatMap(_.split(" "))

  val word2CountDStream = wordDStream.map((_, 1))

  val resultDStream = word2CountDStream.reduceByKey(_ + _)

  resultDStream.print()

  // 启动ssc
  ssc.start()
  ssc.awaitTermination()

}
