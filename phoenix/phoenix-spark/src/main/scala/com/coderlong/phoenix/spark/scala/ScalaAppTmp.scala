package com.coderlong.phoenix.spark.scala

import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.SparkSession
import org.apache.phoenix.spark._
import org.apache.spark.SparkContext

object ScalaAppTmp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("phoenix-test").getOrCreate()
    // 第一种读取方法
    var df = spark.read.format("org.apache.phoenix.spark").option("table", "test1").option("zkUrl", "192.168.56.11:2181").load()
    df = df.filter("name not like 'hig%'")
      .filter("password like '%0%'")
    df.show()

    val configuration = new Configuration()
    configuration.set("hbase.zookeeper.quorum", "192.168.56.11:2181")
    // 第二种读取方法
    df = spark.sqlContext.phoenixTableAsDataFrame("test1", Array("ID", "INFO.NAME", "INFO.PASSWORD"), conf = configuration)
    df.show()

    //第一种输出方法
    df.write
      .format("org.apache.phoenix.spark")
      .mode("overwrite")
      .option("table", "test2")
      .option("zkUrl", "192.168.56.11:2181")
      .save()

    //第二种输出方法
    df.saveToPhoenix(Map("table" -> "test2", "zkUrl" -> "192.168.56.11:2181"))

    //第三种
    //Saving RDDs
//    import org.apache.spark.SparkContext
//    import org.apache.phoenix.spark._
//
    val sc = new SparkContext("local", "phoenix-test")
    val dataSet = List((1L, "1", 1), (2L, "2", 2), (3L, "3", 3))

    sc
      .parallelize(dataSet)
      .saveToPhoenix(
        "OUTPUT_TEST_TABLE",
        Seq("ID","COL1","COL2"),
        zkUrl = Some("phoenix-server:2181")
      )

  }
}
