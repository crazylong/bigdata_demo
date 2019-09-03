package com.mchz.spark.streaming.tjg.util

import java.util.Properties

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object demo9 {
  def main (args : Array[String]) {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("logapp")

    val ssc = new StreamingContext(sparkConf, Seconds(10))

    val lines = ssc.socketTextStream("localhost", 9999).map(x =>x.split(" "))

    lines.foreachRDD(rdd => {
      val sqlContext = SQLContextSingleton2.getInstance(rdd.sparkContext)
      import sqlContext.implicits._
      //构造case class: DapLog,提取日志中相应的字段
      val logDataFrame = rdd.map(w => DapLog(w(0).toInt,w(1),w(2),w(3)+" "+w(4))).toDF()

      //注册为tempTable
      //logDataFrame.registerTempTable("log")
      logDataFrame.createOrReplaceTempView("log")
      //查询该批次的字段   to_timestamp($"event_time", "MM/dd/yyyy HH:mm:ss")
      val logCountsDataFrame = sqlContext.sql("SELECT login_id,user,event_name,to_timestamp(event_time, 'yyyy-MM-dd HH:mm:ss') as event_time,lead(event_time,1,null) over(partition by login_id order by login_id ASC) as nextline_time FROM log")
      //打印查询结果
      val countN = logCountsDataFrame.count()
      if (countN != 0){
        logCountsDataFrame.show()
        //下面保存会出错，我注释掉了/Users/huiliyang/streaming

        //logCountsDataFrame.write.json("/Users/huiliyang/streaming/cc")
        //logCountsDataFrame.write.parquet("/Users/huiliyang/streaming/bb")

        val prop = new Properties()
        prop.put("user", "root")
        prop.put("password", "yh200888")
        prop.put("driver","com.mysql.jdbc.Driver")
        logCountsDataFrame.write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=utf8", "log", prop)
      }

    })
    ssc.start()
    ssc.awaitTermination()

  }
}

case class DapLog(login_id:Int, user:String, event_name:String, event_time:String)

object SQLContextSingleton2 {
  @transient  private var instance: SQLContext = _
  def getInstance(sparkContext: SparkContext): SQLContext = {
    if (instance == null) {
      instance = new SQLContext(sparkContext)
    }
    instance
  }

}