package com.coderlong.influxdb;


import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.List;

public class demo1 {
    public static void main(final String[] args) {

        // You can generate a Token from the "Tokens Tab" in the UI
        String token = "yPtKKlOtg70xrd9l-MIh1Rcf_vDhgP2BC7_iaO-c7azy8mI3RWdKWJnheG3p6dTk_7jfEooXr2ea-WjXxlgx-g==";
        String bucket = "mc_bucket";
        String org = "mc";

        InfluxDBClient client = InfluxDBClientFactory.create("http://192.168.239.128:8086", token.toCharArray());

        String data = "mem,host=host1 used_percent=23.43234543";
        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
        }


        Point point = Point
                .measurement("mem")
                .addTag("host", "host1")
                .addField("used_percent", 23.43234543)
                .time(Instant.now(), WritePrecision.NS);

        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writePoint(bucket, org, point);
        }


        Mem mem = new Mem();
        mem.host = "host1";
        mem.used_percent = 23.43234543;
        mem.time = Instant.now();

        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writeMeasurement(bucket, org, WritePrecision.NS, mem);
        }

        String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);
        List<FluxTable> tables = client.getQueryApi().query(query, org);
        System.out.println(tables);
    }

    @Measurement(name = "mem")
    public static class Mem {
        @Column(tag = true)
        String host;
        @Column
        Double used_percent;
        @Column(timestamp = true)
        Instant time;
    }
}

