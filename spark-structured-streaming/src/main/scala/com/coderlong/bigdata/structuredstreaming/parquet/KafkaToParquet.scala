
package com.coderlong.bigdata.structuredstreaming.parquet

import org.apache.spark.streaming.kafka010.KafkaUtils
/*
object KafkaToParquet {

  val offsetsStore = new ZooKeeperOffsetsStore(conf.getString("zkHosts"), conf.getString("groupId"), conf.getString("topics"))
  val storedOffsets = if (conf.getBoolean("resetOffset")) None else offsetsStore.readOffsets() // this flag helps restart from latest offsets. Needed when we would like to skip some records.

  val kafkaArr =  storedOffsets match {
    case None =>
      // start from the initial offsets
      KafkaUtils.createDirectStream[String,Array[Byte],StringDecoder,DefaultDecoder](ssc, kafkaProps, Set(topics))

    case Some(fromOffsets) =>
      // start from previously saved offsets
      val messageHandler: MessageAndMetadata[String, Array[Byte]] => (String, Array[Byte]) = (mmd: MessageAndMetadata[String, Array[Byte]]) => (mmd.key, mmd.message)
      KafkaUtils.createDirectStream[String,Array[Byte],StringDecoder,DefaultDecoder,Tuple2[String, Array[Byte]]](ssc, kafkaProps, fromOffsets, messageHandler)

  }



  override def saveOffsets(topic: String, rdd: RDD[_]): String =
  {
    val partitionsPerTopicMap = ZkUtils.getPartitionsForTopics(zkClient, List(topic).toSeq)
    val topicDirs = new ZKGroupTopicDirs(consumerGrp, topic)
    LogHandler.log.debug("Saving offsets to ZooKeeper")
    val start = System.currentTimeMillis()
    val offsetsRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
    offsetsRanges.foreach(offsetRange => LogHandler.log.trace(s"Using ${offsetRange}"))
    val offsetsRangesStr = offsetsRanges.map { offsetRange =>
      ZkUtils.updatePersistentPath(zkClient, topicDirs.consumerOffsetDir + "/" + offsetRange.partition, (offsetRange.fromOffset).toString)
      //LogHandler.log.debug("FromOffset is "+ (offsetRange.fromOffset).toString)
      s"${offsetRange.partition}:${offsetRange.fromOffset}"
    }.mkString(",")
    LogHandler.log.debug(s"Writing offsets to ZooKeeper: ${offsetsRangesStr}")
    LogHandler.log.debug("Done updating offsets in ZooKeeper. Took " + (System.currentTimeMillis() - start))
    offsetsRangesStr
  }




  private def getOffsets(groupID :String, topic: String):Option[Map[TopicAndPartition,Option[String]]] = {
    val topicDirs = new ZKGroupTopicDirs(groupID, topic)
    val offsets = new mutable.HashMap[TopicAndPartition,Option[String]]()
    val topicSeq = List(topic).toSeq
    try {
      val topParts = ZkUtils.getPartitionsForTopics(zkClient, topicSeq)
      LogHandler.log.debug("Number of topics are "+ topParts.size +" they are " + topParts.toString())
      var partition:Object=null
      for (top <- topParts) {
        for (partition <- top._2.toList)
        {
          //LogHandler.log.debug("Partition is "+ partition.toString() + " lets access "+ partition._2(0))
          val partitionOffsetPath:String = topicDirs.consumerOffsetDir + "/" + partition;
          val maybeOffset:Option[String] = ZkUtils.readDataMaybeNull(zkClient, partitionOffsetPath)._1;
          LogHandler.log.trace("partitionOffsetPath is "+ partitionOffsetPath + " maybeOffset is "+ maybeOffset.toString + " ZkUtils.readDataMaybeNull(zkClient, partitionOffsetPath)  "+ ZkUtils.readDataMaybeNull(zkClient, partitionOffsetPath).toString())
          val topicAndPartition:TopicAndPartition  = new TopicAndPartition(top._1, partition)
          offsets.put(topicAndPartition, maybeOffset)
        } // All partition offsets for 1 topic
      }
    }
    Option(offsets.toMap)
  }


  // Read the previously saved offsets from Zookeeper

  override def readOffsets: Option[Map[TopicAndPartition, Long]] = {
    LogHandler.log.debug("Reading offsets from ZooKeeper")
    val offsets = new mutable.HashMap[TopicAndPartition, Long]()
    val offsetsRangesMap = getOffsets(consumerGrp, topic)
    val start = System.currentTimeMillis()
    offsetsRangesMap.get.foreach {
      case (tp, offsetOpt) =>
        offsetOpt match {
          case Some(offstr) =>
            LogHandler.log.trace("Done reading offsets from ZooKeeper. Took " + (System.currentTimeMillis() - start))
            val currentOffset:Long = if (Some(offstr).isDefined) Some(offstr).get.toLong else 0L
            offsets.put(tp, currentOffset)
          case None =>
            LogHandler.log.debug("No offsets found in ZooKeeper for " + tp + ". Took " + (System.currentTimeMillis() - start))
            None
        }
    }
    if (offsets.size == 0)
      None
    else
      Option(offsets.toMap)
  }




//Extract data and offsets from DStreams:
  kafkaArr.foreachRDD { (rdd, time) =>
    val offsetSaved = offsetsStore.saveOffsets(topics, rdd).replace(":", "-").replace(",", "_") à indicate the starting point
      LogHandler.log.debug("Saved offset to Zookeeper")
    val timeNow = DateFunctions.timeNow()
    val loadDate = DateFunctions.getdt(timeNow, dateFormats.YYYYMMDD)
    val suffix = StringUtils.murmurHash(offsetSaved)
    val schema = SchemaConverters.toSqlType(BeaconAvroData.getClassSchema).dataType.asInstanceOf[StructType]
    val ardd = rdd.mapPartitions { itr =>
      itr.map { r =>
        try {
          val cr = AvroBeacondataUtils.avroToListWithAudit(r._2, offsetSaved, loadDate, timeNow)
          Row.fromSeq(cr.toArray)
        } catch {
          case e: Exception => LogHandler.log.error("Exception while converting to Avro" + e.printStackTrace())
            System.exit(-1)
            Row(0) //This is just to allow compiler to accept
        }
      }
    }

    val df = sqlctx.createDataFrame(if (conf.getBoolean("reduceOutputPartitions")) ardd.coalesce(conf.getInt("numOutputPartitions"))
    else ardd
      , schema)
    mod_df = setNullableStateForAllColumns(df, true)
    mod_df.save(conf.getString("ParquetOutputPath") + suffix, "parquet", SaveMode.Overwrite)
  }


  import com.google.protobuf.InvalidProtocolBufferException

  //Convert to Avro
  @throws[InvalidProtocolBufferException]
  def getAvroBeaconData(protodata: Array[Byte]): Nothing = {
    val sfLog = Accesslog.accesslog.parseFrom(protodata)
    getAvroBeaconData(sfLog)
  }

  /*public static BeaconAvroData getAvroBeaconData (byte[] protodata)
  throws InvalidProtocolBufferException {
    Accesslog.accesslog sfLog = Accesslog.accesslog.parseFrom(protodata);
    return getAvroBeaconData(sfLog);
  }*/



  public static BeaconAvroData getAvroBeaconData (Accesslog.accesslog sfLog){
    Config conf= AppConfig.conf();
    Map<String,Object> namedBeacon = getNamedBeaconData(sfLog);
    BeaconAvroData avroBeacon = com.edgecast.avro.BeaconAvroData.newBuilder().build();
    if(conf.getBoolean("appendDerivedFields")){
      Enrich.appendBeaconDerivedFields(namedBeacon,avroBeacon);
    }
    try {
      avroBeacon.setXXX(namedBeacon.get("xxx")));  // we need to add each element using its setter method. If there is a way to do this programmatically, we would like to hear. Please leave comments.
    }

    return avroBeacon;
  }




  //Create a sequence from Avro to facilitate Spark DataFrame creation
  public static List avroToList(BeaconAvroData a) throws UnsupportedEncodingException{
    List l = new ArrayList<>();
    for (Schema.Field f : a.getSchema().getFields()) {
      Object value = a.get(f.name());
      if (value == null) {
        l.add(null);
      }
      else {
        switch (f.schema().getType().getName()){
          case "union":
          l.add(value.toString());
          break;
          case "int":
          if(Integer.valueOf(value.toString()).equals(Integer.valueOf(AvroNumericDefault))) {
            l.add(null);
          }
          else {
            l.add(Integer.valueOf(value.toString()));
          }
          break;
          case "long”:
          if(Long.valueOf(value.toString()).equals(Long.valueOf(AvroNumericDefault))) {
            l.add(null);}
          else {
            l.add(Long.valueOf(value.toString()));}
          break;
          default:l.add(value);
          break;
        }
      }
    }
    return l;
  }




  val pd = mod_df.map{r =>
    val n = r.length
    (r(n-4).toString,r(n-3).toString,r(n-2).toString ,r(n-1).toString)}.distinct.collect.toList
  pd.foreach{case (y,m,d,h)=>
    val partdf=sqlctx.sql("select * from validtb where partYear="+ y+ " and partMonth="+ m + " and partDay="+ d + " and partHr="+ h)
    partdf.save(conf.getString("ParquetOutputPath")+"year="+y+"/month="+m+"/day="+d+"/hr="+h+"/"+suffix, "parquet", SaveMode.Overwrite)
    LogHandler.log.info("Created the parquet file"+ conf.getString("ParquetOutputPath")+"year="+y+"/month="+m+"/day="+d+"/hr="+h+"/"+suffix)
  }

}
*/
