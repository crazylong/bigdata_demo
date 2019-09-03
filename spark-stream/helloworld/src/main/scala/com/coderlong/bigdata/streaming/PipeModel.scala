package com.coderlong.bigdata.streaming

import java.util
import java.util.Arrays

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.{Dataset, Row, RowFactory, SQLContext, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
 * 管道模型
 */
object PipeModel {



  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("PipeModelSparkStreaming").setMaster("local[2]")
    //sparkConf.set("spark.streaming.receiverRestartDelay", "5000"); //设置Receiver启动频率，每5s启动一次
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    val ssc = new StreamingContext(sc, Seconds(5))  //设置Spark时间窗口，每10s处理一次

    val rddStream = ssc.receiverStream(new PipeModelReceiver())
    println("rddStream print")
    //rddStream.print()
    rddStream

    //rddStream.saveAsTextFiles("/sparkdemo/test", "001")

    val schema = DataTypes.createStructType(util.Arrays.asList(
      DataTypes.createStructField("vehicle_no", DataTypes.StringType, true),
      DataTypes.createStructField("date_time", DataTypes.StringType, true),
    DataTypes.createStructField("lng", DataTypes.StringType, true),
    DataTypes.createStructField("lat", DataTypes.StringType, true),
    DataTypes.createStructField("velocity1", DataTypes.StringType, true),
    DataTypes.createStructField("velocity2", DataTypes.StringType, true)))

    //VEHICLE_NO, DATE_TIME, LNG, LAT, VELOCITY1, VELOCITY2
    rddStream.foreachRDD(rdd=>{
      val sqlContext = SQLContextSingleton.getSQLContext(rdd.sparkContext)
      import sqlContext.implicits._

      var gpsDataFrame = rdd.map(rowMap => GpsData(rowMap("VEHICLE_NO"), rowMap("DATE_TIME"), rowMap("LNG"),
        rowMap("LAT"), rowMap("VELOCITY1"), rowMap("VELOCITY2"))).toDF()

      gpsDataFrame.createOrReplaceTempView("pipe_model_tmp")


      //rdd.toDF("vehicle_no", "date_time", "lng", "lat", "velocity1", "velocity2").createOrReplaceTempView("pipe_model_tmp")

      var sql = "select * from pipe_model_tmp"

      val actionDF = sqlContext.sql(sql)
      actionDF.show()
    })


/*    rddStream.foreachRDD(rdd=>{
      var rowsBroadcast : Broadcast[ArrayBuffer[Row]] = ssc.sparkContext.broadcast(new ArrayBuffer[Row]())
      rdd.foreach(rowMap=>{
        var row : Row =RowFactory.create(rowMap("VEHICLE_NO"), rowMap("DATE_TIME"), rowMap("LNG"),
          rowMap("LAT"), rowMap("VELOCITY1"), rowMap("VELOCITY2"))
        var rows : ArrayBuffer[Row] = rowsBroadcast.value
        rows += row

        println(rows.mkString(","))
      })

      val sqlContext : SQLContext = new SQLContext(ssc.sparkContext)

      var rows : ArrayBuffer[Row] = rowsBroadcast.value
      var rddRow : RDD[Row] = sc.parallelize(rows)

      var df : Dataset[Row] = sqlContext.createDataFrame(rddRow, schema)

      df.createOrReplaceTempView("pipe_model_tmp")

      var sql = "select * from pipe_model_tmp"
      val actionDF = sqlContext.sql(sql)
      actionDF.show()
    })*/




//    rddStream.foreachRDD(rdd => {
//      rdd.foreachPartition(par => {
//        var rows : ArrayBuffer[Row] = new ArrayBuffer[Row]()
//        par.foreach(rowTuple=>{
//          var row : Row =RowFactory.create(rowTuple._1, rowTuple._2)
//          rows += row
//        })
//
//        val sqlContext : SQLContext = new SQLContext(ssc.sparkContext)
//        var rddRow : RDD[Row] = sc.parallelize(rows)
//
//        var df : Dataset[Row] = sqlContext.createDataFrame(rddRow, schema)
//
//        df.createOrReplaceTempView("pipe_model_tmp")
//
//        var sql = "select * from pipe_model_tmp"
//        val actionDF = sqlContext.sql(sql)
//        actionDF.show()
//      })
//    })

    ssc.start()
    ssc.awaitTermination()
  }


}


case class GpsData(vehicle_no:String, date_time:String, lng:String, lat:String, velocity1:String, velocity1velocity2:String)
