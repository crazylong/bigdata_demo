//package com.coderlong.sparkstreaming.gps;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.StreamingContext;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka010.KafkaUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ConsumerGpsJava {
//    public static void main(String[] args) {
//        SparkConf conf = new SparkConf();
//        conf.setMaster("local");
//        conf.setAppName("SparkStreamingOnKafkaDirect");
//        //设置每个分区每秒读取多少条数据
//        conf.set("spark.streaming.kafka.maxRatePerPartition","1");
//        JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(2));
//
//
//        //设置日志级别
//        ssc.sparkContext().setLogLevel("Error");
//
//        Map<String, Object> kafkaParams = new HashMap<>();
//        kafkaParams.put("bootstrap.servers", "hadoop01:9092,hadoop02:9092,hadoop03:9092");
//        kafkaParams.put("", "");
//        (
//                "bootstrap.servers" -> "hadoop01:9092,hadoop02:9092,hadoop03:9092",
//                "key.deserializer" -> classOf[StringDeserializer],
//                "value.deserializer" -> classOf[StringDeserializer],
//                "group.id" -> "MyGroupId3",//
//
//                /**
//                 * 当没有初始的offset，或者当前的offset不存在，如何处理数据
//                 *  earliest ：自动重置偏移量为最小偏移量
//                 *  latest：自动重置偏移量为最大偏移量【默认】
//                 *  none:没有找到以前的offset,抛出异常
//                 */
//                "auto.offset.reset" -> "earliest",
//
//                /**
//                 * 当设置 enable.auto.commit为false时，不会自动向kafka中保存消费者offset.需要异步的处理完数据之后手动提交
//                 */
//                "enable.auto.commit" -> (false: java.lang.Boolean)//默认是true
//    )
//
//        val topics = Array("gpsTest")
//        val kafkaStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
//                ssc,
//                PreferConsistent,//
//                Subscribe[String, String](topics, kafkaParams)
//    )
//    }
//}
