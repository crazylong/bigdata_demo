package com.coderlong.bigdata.structuredstreaming.cloudtrail

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.types._

object CloudTrailLogs {
  def main(args: Array[String]): Unit = {
    val cloudTrailLogsPath = "s3n://MY_CLOUDTRAIL_BUCKET/AWSLogs/*/CloudTrail/*/2017/01/03/"
    val parquetOutputPath = "/cloudtrail/"  // DBFS or S3 path

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("CloudTrailLogs")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    //Step 3: Let's do streaming ETL on it!
    val rawRecords = spark.readStream
      .option("maxFilesPerTrigger", "100")
      .schema(cloudTrailSchema)
      .json(cloudTrailLogsPath)

    import spark.implicits._

    //Explode (split) the array of records loaded from each file into separate records.
    //Parse the string event time string in each record to Spark’s timestamp type.
    //Flatten out the nested columns for easier querying.
    val cloudTrailEvents = rawRecords
      .select(explode($"Records") as "record")
      .select(
        unix_timestamp($"record.eventTime", "yyyy-MM-dd'T'hh:mm:ss").cast("timestamp") as "timestamp",
        $"record.*")

    val checkpointPath = "/cloudtrail.checkpoint/"

    //Write the data out in the Parquet format,
    //Define the date column from that timestamp and partition the Parquet data by date for efficient time-slice queries.
    //Define the trigger to be every 10 seconds.
    //Define the checkpoint location
    //Finally, start the query.
    val streamingETLQuery = cloudTrailEvents
      .withColumn("date", $"timestamp".cast("date"))
      .writeStream
      .format("parquet")
      .option("path", parquetOutputPath)
      .partitionBy("date")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .option("checkpointLocation", checkpointPath)
      .start()


    //Step 4: Query up-to-the-minute data from Parquet Table
    val parquetData = spark.sql(s"select * from parquet.`$parquetOutputPath`")
    parquetData.show()

    spark.sql(s"select * from parquet.`$parquetOutputPath`").count()
  }



  val cloudTrailSchema = new StructType()
    .add("Records", ArrayType(new StructType()
      .add("additionalEventData", StringType)
      .add("apiVersion", StringType)
      .add("awsRegion", StringType)
      .add("errorCode", StringType)
      .add("errorMessage", StringType)
      .add("eventID", StringType)
      .add("eventName", StringType)
      .add("eventSource", StringType)
      .add("eventTime", StringType)
      .add("eventType", StringType)
      .add("eventVersion", StringType)
      .add("readOnly", BooleanType)
      .add("recipientAccountId", StringType)
      .add("requestID", StringType)
      .add("requestParameters", MapType(StringType, StringType))
      .add("resources", ArrayType(new StructType()
        .add("ARN", StringType)
        .add("accountId", StringType)
        .add("type", StringType)
      ))
      .add("responseElements", MapType(StringType, StringType))
      .add("sharedEventID", StringType)
      .add("sourceIPAddress", StringType)
      .add("serviceEventDetails", MapType(StringType, StringType))
      .add("userAgent", StringType)
      .add("userIdentity", new StructType()
        .add("accessKeyId", StringType)
        .add("accountId", StringType)
        .add("arn", StringType)
        .add("invokedBy", StringType)
        .add("principalId", StringType)
        .add("sessionContext", new StructType()
          .add("attributes", new StructType()
            .add("creationDate", StringType)
            .add("mfaAuthenticated", StringType)
          )
          .add("sessionIssuer", new StructType()
            .add("accountId", StringType)
            .add("arn", StringType)
            .add("principalId", StringType)
            .add("type", StringType)
            .add("userName", StringType)
          )
        )
        .add("type", StringType)
        .add("userName", StringType)
        .add("webIdFederationData", new StructType()
          .add("federatedProvider", StringType)
          .add("attributes", MapType(StringType, StringType))
        )
      )
      .add("vpcEndpointId", StringType)))
}
