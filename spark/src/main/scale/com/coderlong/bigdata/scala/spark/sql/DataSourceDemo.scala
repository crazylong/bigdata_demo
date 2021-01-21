package com.coderlong.bigdata.scala.spark.sql

import org.apache.spark.sql.SaveMode
import org.junit.Test

/**
 *  1.Generic Load/Save Functions
 *  2.Generic File Source Options
 */
class DataSourceDemo {
  /**
   * default datasource: parquet
   */
  @Test
  def test1(): Unit ={
    val spark = SparkUtil.getSparkSession
    val userDF = spark.read.load("src/main/resources/users.parquet")
    userDF.select("name", "favorite_color").write.save("nameAndFavoriteColor.parquet")
    spark.stop()
  }

  /**
   * Manually Specifying Options
   */
  @Test
  def test2(): Unit ={
    val spark = SparkUtil.getSparkSession

    val peopleDF = spark.read.format("json").load("src/main/resources/people.json")
    peopleDF.select("name", "age").write.format("parquet").save("namesAndAges.parquet")
    spark.stop()
  }

  /**
   * csv
   */
  @Test
  def test3(): Unit ={
    val spark = SparkUtil.getSparkSession
    val peopleDFCsv = spark.read.format("csv")
      .option("sep", ";")
      .option("inferSchema", "true")
      .option("header", "true")
      .load("src/main/resources/people.csv")

    peopleDFCsv.select("*").show()
    spark.stop()
  }

  /**
   * orc
   */
  @Test
  def test4(): Unit ={
    val spark = SparkUtil.getSparkSession
    val usersDF = spark.read.load("src/main/resources/users.parquet")
    usersDF.write.format("orc")
      .option("orc.bloom.filter.columns", "favorite_color")
      .option("orc.dictionary.key.threshold", "1.0")
      .option("orc.column.encoding.direct", "name")
      .save("users_with_options.orc")
    spark.stop()
  }

  /**
   * Run SQL on file directly
   */
  @Test
  def test5(): Unit ={
    val spark = SparkUtil.getSparkSession
    spark.sql("SELECT * FROM parquet.`src/main/resources/users.parquet`").show()
    spark.stop()
  }


  /**
   * SaveMode
   */
  @Test
  def test6(): Unit ={
    val spark = SparkUtil.getSparkSession
    val userDF = spark.read.load("src/main/resources/users.parquet")
    userDF.select("name", "favorite_color")
      .write
      .mode(SaveMode.Append)
      .save("nameAndFavoriteColor.parquet")
    spark.stop()
  }

  /**
   * Saving to Persistent Tables
   */
  @Test
  def test7(): Unit ={
    val spark = SparkUtil.getSparkSession
    val userDF = spark.read.load("src/main/resources/users.parquet")
    userDF.write
      .option("path", "hive_user")
      .saveAsTable("t_user")
    spark.stop()
  }

  @Test
  def test8(): Unit ={
    val spark = SparkUtil.getSparkSession
    spark.sql("select * from t_user").show()
    spark.stop()
  }

  /**
   * Bucketing
   */
  @Test
  def test9(): Unit ={
    val spark = SparkUtil.getSparkSession

    val peopleDF = spark.read.format("json").load("src/main/resources/people.json")

    peopleDF.write
      .bucketBy(42, "name")
      .sortBy("age")
      .saveAsTable("people_bucketed")
    spark.stop()
  }

  @Test
  def test10(): Unit ={
    val spark = SparkUtil.getSparkSession
    spark.sql("select * from people_bucketed").show()
    spark.stop()
  }


  /**
   * Partitioning
   */
  @Test
  def test11(): Unit ={
    val spark = SparkUtil.getSparkSession

    val userDF = spark.read.load("src/main/resources/users.parquet")

    userDF.write.format("parquet").partitionBy("favorite_color").save("namesPartByColor.parquet")
  }

  /**
   * Partitioning
   */
  @Test
  def test12(): Unit ={
    val spark = SparkUtil.getSparkSession

    val userDF = spark.read.load("src/main/resources/users.parquet")

    userDF.write
      .partitionBy("favorite_color")
      .bucketBy(42, "name")
      .saveAsTable("users_partitioned_bucketed")
  }

  /**
   * Ignore Corrupt Files
   * Ignore Missing Files
   */
  @Test
  def test13(): Unit ={
    val spark = SparkUtil.getSparkSession
    //spark.sql("set spark.sql.files.ignoreCorruptFiles=true")

    //spark.sql("set spark.sql.files.ignoreMissingFiles=true")
    val testCorruptDF = spark.read.parquet(
      "src/main/resources/dir1/",
      "src/main/resources/dir1/dir2/"
    )
    testCorruptDF.show()
    spark.stop()
  }

  /**
   * Path Global Filter
   */
  @Test
  def test14(): Unit ={
    val spark = SparkUtil.getSparkSession
    val testGlobFilterDF = spark.read.format("parquet")
      .option("pathGlobFilter", "*.parquet")
      .load("src/main/resources/dir1/")
    testGlobFilterDF.show()
  }

  @Test
  def test15(): Unit ={
    val spark = SparkUtil.getSparkSession
    spark.sql("set spark.sql.files.ignoreCorruptFiles=true")
    val recursiveLoadedDF = spark.read.format("parquet")
      .option("recursiveFileLookup", "true")
      .load("src/main/resources/dir1/")
    recursiveLoadedDF.show()
  }

}
