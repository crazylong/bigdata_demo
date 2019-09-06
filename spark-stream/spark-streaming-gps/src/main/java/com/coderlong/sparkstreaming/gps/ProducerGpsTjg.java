package com.coderlong.sparkstreaming.gps;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerGpsTjg {
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
        mockData(producer);
    }

    private static void mockData(KafkaProducer<String, String> producer){
        String gps = "{\"alarm03\":false,\"alarm04\":false,\"vehicle_color\":2,\"alarm05\":false,\"alarm06\":false,\"alarm00\":false,\"alarm01\":false,\"alarm02\":false,\"alarm07\":false,\"alarm08\":false,\"alarm09\":false,\"state19\":true,\"state18\":true,\"date_time\":1566981802000,\"state13\":false,\"state12\":false,\"state11\":false,\"state10\":false,\"state17\":false,\"state16\":false,\"lat\":38843871,\"state15\":false,\"mileage\":218649,\"state14\":false,\"alarm14\":false,\"alarm15\":false,\"alarm16\":false,\"alarm17\":false,\"lng\":117257295,\"alarm10\":false,\"alarm11\":false,\"alarm12\":false,\"alarm13\":false,\"alarm18\":false,\"alarm19\":false,\"state09\":false,\"state08\":false,\"state07\":false,\"state02\":false,\"state01\":true,\"state00\":true,\"state06\":false,\"state05\":false,\"state04\":false,\"state03\":false,\"alarm25\":false,\"altitude\":0,\"alarm26\":false,\"alarm27\":false,\"alarm28\":false,\"state31\":false,\"alarm21\":false,\"state30\":false,\"alarm22\":false,\"alarm23\":false,\"alarm24\":false,\"alarm29\":false,\"update_time\":1566981805000,\"encrypt\":false,\"alarm20\":false,\"direction\":40,\"velocity2\":77,\"state20\":false,\"velocity1\":76,\"vehicle_no\":\"冀AEB705\",\"state29\":false,\"state24\":true,\"state23\":false,\"state22\":false,\"alarm30\":false,\"state21\":false,\"alarm31\":false,\"state28\":false,\"state27\":false,\"state26\":false,\"state25\":false}";
        JSONObject jpsJson = JSONObject.parseObject(gps);
        Long lng = jpsJson.getLong("lng");
        Long lat = jpsJson.getLong("lat");
        Long dateTime = jpsJson.getLong("date_time");

        for(int i = 0; i < 10000000; i++){
            jpsJson.put("lng", lng + i);
            jpsJson.put("lat", lat + i);
            jpsJson.put("date_time", dateTime + (i * 1000));
            producer.send(new ProducerRecord<>("twelve_ton_topic",  jpsJson.toJSONString()));
        }

    }

}
