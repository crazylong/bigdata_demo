package com.coderlong.bigdata.structuredstreaming.kafka

import java.util.{Date, Properties}

import com.alibaba.fastjson.JSONObject
import org.apache.commons.lang3.RandomStringUtils
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.junit.Test

class MockData {


  @Test
  def mockDeviceData: Unit ={

    // 得到生产者的实例
    val producer = new KafkaProducer[String, String](getKafkaConfig())

    //2020-10-01 10:00:00
    val baseTime: Long = 1601517600
    val deviceTypeArr = Array("类型1", "类型2", "类型3", "类型4", "类型5", "类型6", "类型7")
    // 模拟一些数据并发送给kafka
    for (i <- 1 to 2) {
      val deviceData : JSONObject = new JSONObject()

      deviceData.put("device","device" + i)
      deviceData.put("deviceType",deviceTypeArr(i%7))
      deviceData.put("signal", RandomStringUtils.randomAlphabetic(10))
      deviceData.put("time",System.currentTimeMillis())

      val msg = deviceData.toJSONString
      println("send -->" + msg)

      // 得到返回值
      val rmd: RecordMetadata = producer.send(new ProducerRecord[String, String]("device_data_topic2", msg)).get()
      println(rmd.toString)
      Thread.sleep(1000)
    }

    producer.close()
  }

  def getKafkaConfig(): Properties = {
    val prop = new Properties
    // 指定请求的kafka集群列表
    prop.put("bootstrap.servers", "192.168.242.70:9092,192.168.242.71:9092,192.168.242.72:9092")// 指定响应方式
    //prop.put("acks", "0")
    prop.put("acks", "all")
    // 请求失败重试次数
    //prop.put("retries", "3")
    // 指定key的序列化方式, key是用于存放数据对应的offset
    prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // 指定value的序列化方式
    prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // 配置超时时间
    prop.put("request.timeout.ms", "60000")
    //prop.put("batch.size", "16384")
    //prop.put("linger.ms", "1")
    //prop.put("buffer.memory", "33554432")
    prop
  }

}
