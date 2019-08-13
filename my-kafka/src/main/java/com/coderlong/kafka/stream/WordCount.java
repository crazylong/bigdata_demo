//package com.coderlong.kafka.stream;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.Topology;
//import org.apache.kafka.streams.kstream.KStream;
//
//import java.util.Arrays;
//import java.util.Properties;
//import java.util.concurrent.CountDownLatch;
//
//public class WordCount {
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-demo");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//
//        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
//        // Note: To re-run the demo, you need to use the offset reset tool:
//        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
//        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//
//        StreamsBuilder builder = new StreamsBuilder();
//        KStream<String, String> source = builder.stream("gpsTest2");
//        source.foreach((k, v)->{
//            System.out.println(k + "------" + v);
//        });
//
////        source.flatMapValues(value -> Arrays.asList(value.split("\\W+")))
////                .to("streams-linesplit-output");
//
//
//        final Topology topology = builder.build();
//
//        final KafkaStreams streams = new KafkaStreams(topology, props);
//        //streams.start();
//
//        final CountDownLatch latch = new CountDownLatch(1);
//        // attach shutdown handler to catch control-c
//        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
//            @Override
//            public void run() {
//                streams.close();
//                latch.countDown();
//            }
//        });
//
//        try {
//            streams.start();
//            latch.await();
//        } catch (Throwable e) {
//            System.exit(1);
//        }
//        System.exit(0);
//
//
//        //KStream<String, String> source = builder.stream("gpsTest2");
////        source.foreach((k, v)->{
////            System.out.println(k + "------" + v);
////        });
////        KafkaStreams streams = new KafkaStreams(builder, props);
////        KTable<String, Long> counts = source
////                .flatMapValues(new ValueMapper<String, Iterable<String>>() {
////                    @Override
////                    public Iterable<String> apply(String value) {
////                        return Arrays.asList(value.toLowerCase(Locale.getDefault()).split(" "));
////                    }
////                }).map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
////                    @Override
////                    public KeyValue<String, String> apply(String key, String value) {
////                        return new KeyValue<>(value, value);
////                    }
////                })
////                .groupByKey()
////                .count("Counts");
////        counts.print();
////        KafkaStreams streams = new KafkaStreams(builder, props);
//
//
//
////        final CountDownLatch latch = new CountDownLatch(1);
////        // attach shutdown handler to catch control-c
////        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
////            @Override
////            public void run() {
////                streams.close();
////                latch.countDown();
////            }
////        });
////
////        try {
////            streams.start();
////            latch.await();
////        } catch (Throwable e) {
////            e.printStackTrace();
////        }
//    }
//}
