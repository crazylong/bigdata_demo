package com.coderlong.bigdata.structuredstreaming

import java.sql.{Connection, ResultSet, Statement}

import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.StreamingQueryListener
import org.apache.spark.sql.streaming.StreamingQueryListener.{QueryProgressEvent, QueryStartedEvent, QueryTerminatedEvent}
import org.apache.spark.sql.types.{StringType, _}
import org.apache.spark.sql.{DataFrame, SparkSession}


object StructuredStreaming {
  def main(args: Array[String]): Unit = {
    if(args.isEmpty || args(0).isEmpty){
      println("kafka topic is empty!")
      System.exit(-1)
    }

    //topicName__jobName
    val topic = args(0).split("__")(0)

    if(topic.isEmpty){
      println("kafka topic is empty!")
      System.exit(-1)
    }

    val jobName = args(0).split("__")(1)

    val sql = if(args(1).isEmpty) "" else args(1)

    //println("topic=" + topic + "sql=" + sql)

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName(args(0))
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    spark.streams.addListener(new StreamingQueryListener() {
      //任务启动获取spark job id保存
      override def onQueryStarted(queryStarted: QueryStartedEvent): Unit = {
        println("Query started: " + queryStarted.id)

      }
      override def onQueryTerminated(queryTerminated: QueryTerminatedEvent): Unit = {
        println("Query terminated: " + queryTerminated.id)
      }
      override def onQueryProgress(queryProgress: QueryProgressEvent): Unit = {
        println("Query made progress: " + queryProgress.progress)
      }
    })

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.242.70:9092,192.168.242.71:9092,192.168.242.72:9092")
      .option("subscribe", topic)
      .option("startingOffsets","earliest")//"latest" for streaming, "earliest" for batch
      .load()


    df.printSchema()

    val parsed = df.select(from_json(col("value").cast(StringType) , ygbSchema).alias("parsed_value"), col("timestamp").cast(TimestampType))
    //可以使用struct函数或SQL中的括号来创建新的struct
    //events.select(struct('a as 'y) as 'x)
    //SQL: select named_struct("y", a) as x from events

    //（*）也可用于在嵌套结构中包括所有列
    //Scala: events.select(struct("*") as 'x)
    //SQL: select struct(*) as x from events

    //getItem()或方括号（即[ ]）可用于从数组或地图中选择单个元素
    //Scala: events.select('a.getItem(0) as 'x)
    //SQL: select a[0] as x from events
    // Scala: events.select('a.getItem("b") as 'x)
    //   SQL: select a['b'] as x from events

    //explode()可用于为数组中的每个元素或每个键值对创建新行。这类似于HiveQL中的LATERAL VIEW EXPLODE
      // Scala: events.select(explode('a) as 'x)
    //   SQL: select explode(a) as x from events
    //Scala: events.select(explode('a) as Seq("x", "y"))
    //SQL: select explode(a) as (x, y) from events

    
    //collect_list()并且collect_set()可以用于将项目聚合到一个数组中。



    parsed.printSchema()

    val ygData = parsed
      .select("parsed_value.*", "timestamp")

    ygData.printSchema()

    ygData.createOrReplaceTempView("dfView")
     val frame: DataFrame = spark.sql(sql)
     //val frame: DataFrame = spark.sql("select ZW, count(1) from dfView group by ZW")

     val dfStream = frame.writeStream
       .format("parquet")
       .option("path","D://example/streamingtripdata")
       .option("checkpointLocation", "D://example/streamcheckpoint")
       .start()

    //df.write.parquet("hdfs://data.parquet")

    dfStream.awaitTermination()

    //region window
//    val windowDuration = "20 seconds"
//    val slideDuration = "10 seconds"
//
//    val zwWindowCounts = ygData
//      .withWatermark("timestamp", "20 seconds")
//      .groupBy(window($"timestamp", windowDuration, slideDuration), $"ZW")
//      .count()
      // .orderBy("window")
    //Sorting is not supported on streaming DataFrames/Datasets, unless it is on aggregated DataFrame/Dataset in Complete output mode

    // Start running the query that prints the windowed word counts to the console
//    val query = zwWindowCounts.writeStream
//      .outputMode("update")//由于采用聚合操作，所以需要指定"complete"输出形式
//      .format("console")
//      .option("truncate", "false")//在控制台输出时，不进行列宽度自动缩小
//      // .trigger(Trigger.ProcessingTime("10 seconds"))
//      .start()
//
//    query.awaitTermination()
    //endregion
  }

  val ygbSchema = new StructType()
    .add("GH", StringType)
    .add("YXJGDM", StringType)
    .add("NBJGH", StringType)
    .add("JRXKZH", StringType)
    .add("YXJGMC", StringType)
    .add("XM", StringType)
    .add("SFZH", StringType)
    .add("LXDH", StringType)
    .add("WDH", StringType)
    .add("SSBM", StringType)
    .add("ZW", StringType)
    .add("YGZT", StringType)
    .add("GWBH", StringType)
    .add("CJRQ", StringType)
}
