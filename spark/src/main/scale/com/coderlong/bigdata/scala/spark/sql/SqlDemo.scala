package com.coderlong.bigdata.scala.spark.sql

import org.junit.Test


class SqlDemo {
  /**
   * Untyped Dataset operations(aka DataFrame Operations)
   */
  @Test
  def test1 = {
    val spark = SparkUtil.getSparkSession

    val df = spark.read.json("src/main/resources/people.json")
    df.show()

    df.printSchema()

    df.select("name").show()

    import spark.implicits._

    df.select($"name", $"age" + 1).show()
    //df.select("name", $"age" + 1).show() //要么都用$，要么都不用，不然编译错误
    //df.select("name", "age" + 1).show()//表达式"age"+1必须使用 $"age" + 1
    df.select("name").show()
    df.select("name", "age").show()

    df.filter($"age" > 21).show()

    df.groupBy("age").count().show()

    df.groupBy($"age").count().show()

    spark.stop()
  }


  /**
   * Running SQL Queries Programmatically
   */
  @Test
  def test2 = {
    val spark = SparkUtil.getSparkSession

    val df = spark.read.json("src/main/resources/people.json")

    //Register the DataFrame as a SQL temporary view
    df.createOrReplaceTempView("people")

    val sqlDf = spark.sql("select * from people")

    sqlDf.show()

    //Global temporary view is tied to a system preserved database `global_temp`
    df.createGlobalTempView("people")
    spark.sql("select * from people").show()

    spark.newSession().sql("select * from global_temp.people").show

    spark.stop()
  }


  /**
   * Creating Datasets
   */
  @Test
  def test3(): Unit ={
    val spark = SparkUtil.getSparkSession
    import spark.implicits._
    val caseClassDS = Seq(Person("Andy", 32)).toDS()

    caseClassDS.show()

    val primitiveDS = Seq(1, 2, 3).toDS()
    primitiveDS.map(_ + 1).collect().foreach(println(_))
    primitiveDS.show()

    // DataFrames can be converted to a Dataset by providing a class.
    // Mapping will be done by name
    val path = "src/main/resources/people.json";
    val peopleDS = spark.read.json(path).as[Person]
    peopleDS.show()

    spark.stop()
  }

  //Interoperating with RDDs
  /**
   * 1.Inferring the Schema Using Reflection
   */
  @Test
  def test04(): Unit ={
    val spark = SparkUtil.getSparkSession
    import spark.implicits._
    //val peopleDF = spark.read.text()
    val peopleDF = spark.sparkContext
      .textFile("src/main/resources/people.txt")
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim().toInt))
      .toDF()

    peopleDF.createOrReplaceTempView("people")

    val teenagersDF = spark.sql("select name, age FROM people where age between 13 and 19")

    //The columns of a row in the result can be accessed by field index
    teenagersDF.map(teenager => "Name" + teenager(0)).show()

    //by field name
    teenagersDF.map(teenager => "Name" + teenager.getAs[String]("name")).show()

    // No pre-defined encoders for Dataset[Map[K,V]], define explicitly
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
    // Primitive types and case classes can be also defined as
    // implicit val stringIntMapEncoder: Encoder[Map[String, Any]] = ExpressionEncoder()

    // row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
    teenagersDF
      .map(teenager => teenager.getValuesMap[Any](List("name", "age")))
      .collect()
      .foreach(print(_))


    spark.stop()
  }

  /**
   * 2.Programmatically Specifying the Schema
   */
  @Test
  def test5(): Unit ={
    import org.apache.spark.sql.Row
    import org.apache.spark.sql.types._


    val spark = SparkUtil.getSparkSession
    import spark.implicits._
    val peopleRDD = spark.sparkContext.textFile("src/main/resources/people.txt")


    val schemaString = "name age"

    val fields = schemaString.split(" ")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    val rowRDD = peopleRDD
          .map(_.split(","))
          .map(attributes => Row(attributes(0), attributes(1).trim))

    //Apply the schema to the RDD
    val peopleDF = spark.createDataFrame(rowRDD, schema)

    peopleDF.createOrReplaceTempView("people")

    val results = spark.sql("select name from people")

    results.map(attributes => "Name:" + attributes(0)).show

  }




  

}

case class Person(name: String, age: Long)
