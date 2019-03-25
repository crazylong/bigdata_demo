package com.coderlong.bigdata.kafka

import kafka.api.TopicMetadataRequest
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import kafka.serializer.StringDecoder
import kafka.utils.ZKGroupTopicDirs
import org.I0Itec.zkclient.ZkClient
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * @author Long Qiong 
  * @create 2019/3/19
  */
object Streaming2Kafka {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("kafka").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val fromTopic = "from1"
    val toTopic = "to1"

    val brokers = "cdh02-03:9092,cdh02-04:9092";
    //val zookeeper = "";

    val kafkaPro = Map[String, String](
      "bootstrap.servers" -> brokers,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG->"org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG->"org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.GROUP_ID_CONFIG->"kafka",
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "largest"
    )

//    //获取保存offset的zk路劲
//    val topicDirs = new ZKGroupTopicDirs("kafka", fromTopic)
//    val zkTopicPath = s"${topicDirs.consumerOffsetDir}"
//
//    //创建一个ZK的连接
//    val zkClient = new ZkClient(zookeeper)
//
//    //获取偏移的保存地址目录下的子节点
//    val children = zkClient.countChildren(zkTopicPath)
//
//    //>0 说明有保存过
//    if(children >0 ){
//      //新建一个变量，保存消费的偏移量
//      var fromOffsets:Map[TopicAndPartition, Long] = Map()
//
//      //首先获取每一个Partition的主节点的信息
//      val topicList = List(fromTopic)
//      //创建一个获取元信息的请求
//      val request = new TopicMetadataRequest(topicList, 0)
//      val getLeaderConsumer = new SimpleConsumer("master01", 9092, 100000, 1000, "OffsetLookUp")
//      val response = getLeaderConsumer.send(request)
//
//      val topicMeteOption = response.topicsMetadata.headOption
//
//      val partitions = topicMeteOption match {
//        case Some(tm) => {
//          tm.partitionsMetadata.map(pm => (pm.partitionId, pm.leader.get.host)).toMap[Int, String]
//        }
//        case None => {
//          Map[Int, String]()
//        }
//      }
//
//      getLeaderConsumer.close()
//      println("partitions information is :" + partitions)
//      println("children infomation is:" + children)
//
//      for(i< -0 util children){
//        //获取保存在ZK中的偏移信息
//        //todo
//      }
//    }

    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaPro, Set(fromTopic))

    stream.map{ case(key, value) => "ABC:" + value}.foreachRDD{rdd =>
      rdd.foreachPartition{ items =>
        //写回kafka连接池
        val kafkaProxyPool = KafkaPool(brokers)
        val kafkaProxy = kafkaProxyPool.borrowObject()

        for(item <- items){
          println(item)
          //使用
          kafkaProxy.kafkaClient.send(new ProducerRecord[String, String](toTopic, item))
        }


        kafkaProxyPool.returnObject(kafkaProxy)
      }
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
