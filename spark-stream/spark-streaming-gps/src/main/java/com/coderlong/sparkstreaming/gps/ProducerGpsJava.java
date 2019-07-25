package com.coderlong.sparkstreaming.gps;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerGpsJava {
    private static Random random = new Random(1000000000);
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //props.put("partition", 6);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        //producer.send(new ProducerRecord<>("gpsTest2",  mockData2().toJSONString()));
        while (true){
            producer.send(new ProducerRecord<>("gpsTest2",  mockData2().toJSONString()));

        }
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        while (true){
//            executorService.submit(()->{
//                producer.send(new ProducerRecord<>("gpsTest",  mockData().toJSONString()));
//                try {
//                    Thread.sleep(5);
//                } catch (InterruptedException e) {}
//            });
//        }
    }

    private static JSONObject mockData(){
        JSONObject jpsJson;
        String uuid = UUID.randomUUID().toString();
//        String info = "{\"method\": \"REGISTER\",\"type\": \"register_req\",\"call_id\": \"" + uuid + "\",\"info\": {\"certificate\": {\"info\": {\"ver\": 2,\"sn\": \"{5E2D9D5F-8BB9-4E3C-914D-A0E8755CD81B}\",\"user\": \"宁波港信息通信有限公司\",\"begin\": \"2017-12-28\",\"end\": \"2018-12-27\",\"area\": \"\",\"ip\": \"120.26.226.182\",\"idlist\": \"\",\"subscribe\": \"@gps@809\",\"notify\": \"\",\"send\": \"gps_query_req\",\"limit\": 3,\"localhost\": false,\"email\": \"664976740@qq.com\",\"tel\": \"15742768798\",\"server_id\": \"\"},\"check\": \"654f257dde3055511cff7ee95651ad21\"}}}";

        String randomNo = "浙" + String.valueOf(random.nextLong());
        String time = String.valueOf(System.currentTimeMillis());
        String info = "{\"method\":\"NOTIFY\",\"type\":\"51234@gps@809\",\"from\":\"{A74030B9-64FB-463C-8E1C-A50965D25F41}\",\"to\":\"\",\"call_id\":\"F099E56A-9D89-4326-A36F-7D4BB8A19456\",\"info\":{\"NO\":\"" + randomNo + "\",\"area1\":\"B\",\"area2\":0,\"color\":2,\"data_type\":3,\"gnsscenterid\":51234,\"gps\":{\"alarm\":0,\"altitude\":0,\"cmb_date\":\"2019-06-17 18:46:17\",\"direction\":181,\"excrypt\":0,\"lat\":\"29.877796\",\"lon\":\"121.889571\",\"state\":3,\"time\":\"" + time + "\",\"vec1\":2,\"vec2\":3,\"vec3\":285312},\"type\":0}}";

        jpsJson = JSONObject.parseObject(info);
        return jpsJson;
    }

    private static JSONObject mockData2(){
        JSONObject jpsJson;
        String uuid = UUID.randomUUID().toString();

        String randomNo = "湘" + String.valueOf(random.nextLong());
        String time = String.valueOf(System.currentTimeMillis());
        String info = "{\"NO\":\"" + randomNo + "\",\"area1\":\"B\",\"area2\":0,\"color\":2,\"data_type\":3,\"gnsscenterid\":51234,\"alarm\":0,\"altitude\":0,\"cmb_date\":\"2019-06-17 18:46:17\",\"direction\":181,\"excrypt\":0,\"lat\":\"29.877796\",\"lon\":\"121.889571\",\"state\":3,\"time\":\"" + time + "\",\"vec1\":2,\"vec2\":3,\"vec3\":285312,\"type\":0}";

        jpsJson = JSONObject.parseObject(info);
        return jpsJson;
    }
}
