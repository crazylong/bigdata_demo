package com.coderlong.bigdata.streaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("streamingTest")
    val sc = new SparkContext(conf)

    //val ssc = new StreamingContext(conf, Seconds(5))
    //val ssc = new StreamingContext(conf, Durations.seconds(5))
    //val ssc = new StreamingContext(sc, Durations.seconds(5))
    val ssc = new StreamingContext(sc, Seconds(10))
    ssc.sparkContext.setLogLevel("ERROR")
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("spark", 9999)

    val words: DStream[String] = lines.flatMap(line => {
      line.split(" ")
    })

    val pairWords: DStream[(String, Int)] = words.map(word => {
      (word, 1)
    })

    val result: DStream[(String, Int)] = pairWords.reduceByKey(_+_)

   // result.print(100)

//    result.foreachRDD(wordCountRDD=>{
//      println("*********producer in Driver **********")
//      val sortRDD: RDD[(String, Int)] = wordCountRDD.sortByKey(false)
//      val result: RDD[(String, Int)] = sortRDD.filter(tp => {
//        println("*****************producer in Executor ********8")
//        true
//      })
//      result.foreach(println)
//    })


    //val count = result.count()
    //println(count)
   // println("=====result.count=======" + result.count())
//   result.foreachRDD(pairRDD=>{
//      println("=====drive执行=======")
//    })
//    println(result.count())

    result.foreachRDD(pairRDD=>{
      println("==============================")
      val sortRDD = pairRDD.sortByKey(false)
      sortRDD.foreach(s=>{
        println(s._1 + "=> " + s._2)
      })

      val filterRDD = sortRDD.filter(sort=>{
        sort._2>1
      })
      println("*****" + filterRDD.count())
    })



    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
    ssc.stop(false)





  }
}
