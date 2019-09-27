package com.coderlong.phoenix.spark.scala

import java.util.Properties

import com.alibaba.fastjson.serializer.SerializeFilter
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.{DataFrame, Dataset, Encoders, SparkSession}
import org.apache.phoenix.spark._
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object GpsVacuate {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[4]")
      .appName("phoenix-test")
      .getOrCreate()


    val prop : Properties = new Properties()
    prop.put("phoenix.schema.isNamespaceMappingEnabled", "true")
    val df = spark.read.format("org.apache.phoenix.spark")
      .jdbc("jdbc:phoenix:hadoop01,hadoop02,hadoop03", "lq.vehicle_info", prop)
      .filter("VEHICLE_NO='津AT3758'")
      .select("VEHICLE_NO").distinct()

    val vehicleNoArr = df.rdd.map(row => row.getAs[String]("VEHICLE_NO")).collect()

    for (vehicleNo <- vehicleNoArr) {
      val dfGps = spark.read.format("org.apache.phoenix.spark")
        .jdbc("jdbc:phoenix:hadoop01,hadoop02,hadoop03", "lq.twelve_ton4", prop)
        .filter("VEHICLE_NO='" + vehicleNo + "'")

      val dfGpsRdd = dfGps.rdd
      if(!dfGpsRdd.isEmpty()){
        val gpsJsonArr: Array[JSONObject] = dfGpsRdd.map(row => {
          val rowJson: JSONObject = new JSONObject()

          for (field <- row.schema.fieldNames) {
            rowJson.put(field, row.getAs[String](field))
          }

          rowJson
        }).collect()

        if(!gpsJsonArr.isEmpty){
          println("分割前：" + vehicleNo + ":" + gpsJsonArr.size)
          val vacuateGps = DouglasPeucker.reduction(gpsJsonArr, 50000)
          println("分割后：" + vacuateGps.size)

          val gpsList: List[String] = vacuateGps.map(gps => {
            JSON.toJSONString(gps, new Array[SerializeFilter](0))
          })

          val gpsDS: Dataset[String] = spark.createDataset(gpsList)(Encoders.STRING)

          val gpsVacuateDF: DataFrame = spark.sqlContext.read.json(gpsDS)

          gpsVacuateDF.saveToPhoenix("lq.VACUATE_GPS2",new Configuration(), zkUrl=Option("hadoop01:2181"))
        }
      }
    }
  }
}
