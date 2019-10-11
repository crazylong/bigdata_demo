package com.coderlong.bigdata.scala.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

object ReadTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("readtest")
      .master("local")
      .getOrCreate()
//    val pathjson = "C:\\notos\\code\\sparktest\\src\\main\\resources\\employees.json"
//    val pathavsc = "C:\\notos\\code\\sparktest\\src\\main\\resources\\full_user.avsc"
//    val pathtxt = "C:\\notos\\code\\sparktest\\src\\main\\resources\\people.txt"
    val pathcsv = "D:\\code\\bigdata_demo\\spark\\src\\main\\resources.bak\\gps.csv"
//    val pathparquet = "C:\\notos\\code\\sparktest\\src\\main\\resources\\users.parquet"
    val sc = spark.sparkContext
    println(s"-----------------read--------------------------")

//    println("====txt df=============================")
//    val txtrdd = sc.textFile(pathtxt).map(_.split(",")).map(arr => Row.fromSeq(arr))
//    val schemaString = "name age"
//    val fields = schemaString.split(" ")
//      .map(fieldName => StructField(fieldName, StringType, nullable = true))
//    val schema = StructType(fields)
//    val txtDf = spark.createDataFrame(txtrdd, schema)
//    txtDf.show()

//    println("====json df============================") //jsondf 会自动给schema设置类型
//    val jsonDf = spark.read.json(pathjson)
//    jsonDf.show()

    println("==== csvdf=============================")
    //会根据值自动生成类型
    val csvdf = spark.read.format("csv")
      .option("sep", ",")
      .option("inferSchema", "true")
      .option("header", "true")
      .load(pathcsv)
    csvdf.show()
   csvdf.rdd.groupBy(c => {
      c.getAs[Int]("vehicle_no")
    }).foreach(d=>{
      println(d._1 + "====>" + d._2)
    })

    //println(value)

//    println("====parquet df==============================")
//    val usersDF = spark.read.load(pathparquet)
//    usersDF.show()

    println("----------------------------------write-------------------------------")
//    val path = "C:\\notos\\code\\sparktest\\src\\main\\"
//
//    println(s"====txt output")
//    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[String]
//    csvdf
//      .write
//      .format("csv")
//      .mode(SaveMode.Append)
//      .options(Map("compression" -> "bzip2", "sep" -> "\t", "header" -> "false"))
//      .save(path + "\\text")
//
//    println(s"====csv output")
//    csvdf.write.mode(SaveMode.Ignore)
//      .format("csv")
//      .option("sep", "|")
//      .option("header", "true")
//      .save(s"$path\\csv")
//
//    println(s"====json output")
//    csvdf.write.mode(SaveMode.Append)
//      .format("json")
//      .save(path + "\\json")
//
//    println(s"====parquet output")
//    csvdf.write.mode(SaveMode.Append)
//      .format("parquet")
//      .save(s"$path\\parquet")
    spark.stop()
  }
}
