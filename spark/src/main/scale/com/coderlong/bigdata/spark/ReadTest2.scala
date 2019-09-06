package com.coderlong.bigdata.spark

import com.alibaba.fastjson.JSONObject
import com.coderlong.bigdata.spark.vacuate.DouglasPeucker
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

object ReadTest2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("readtest")
      .master("local")
      .getOrCreate()
    val pathcsv = "D:\\code\\bigdata_demo\\spark\\src\\main\\resources.bak\\gps2.csv"
    val sc = spark.sparkContext
    println(s"-----------------read--------------------------")


    println("==== csvdf=============================")
    //会根据值自动生成类型
    val csvdf = spark.read.format("csv")
      .option("sep", ",")
      .option("inferSchema", "true")
      .option("header", "true")
      .load(pathcsv)
    csvdf.show()

    var result = ArrayBuffer[JSONObject]()
    var resultBroad: Broadcast[ArrayBuffer[JSONObject]] = sc.broadcast(result)
   var group = csvdf.rdd.groupBy(c => {
     (c.get(c.fieldIndex("vehicle_no")), c.get(c.fieldIndex("lng")), c.get(c.fieldIndex("lat")))
    })
     .map(d=>d._2.toList.head)
     .groupBy(e=>{
       e.get(e.fieldIndex("vehicle_no"))
     })

    group.foreach(f=>{
        f._2.foreach(g=>{
          var json : JSONObject = new JSONObject()
          g.schema.fieldNames.foreach(h=>{
            json.put(h, g.get(g.fieldIndex(h)))
          })
          resultBroad.value += json
          //result += json
        })
     })

    //println(s)

    println(resultBroad.value)

    val vacuageGps = DouglasPeucker.reduction(resultBroad.value.toArray, 100)

    println("----------------------------------write-------------------------------")

    spark.stop()
  }
}
