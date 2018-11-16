package com.coderlong.bigdata.java.sparkcdh.funnel;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.List;
import java.util.StringJoiner;

import static org.apache.spark.sql.functions.expr;

public class FunnelAnalysis {

    public static void main(String[] args) {
        String path = args[0];
        Long window = Long.parseLong(args[1]) * 1000;
        String[] eventIds = args[2].split(",");
        String start = args[3];
        String end = args[4];

        SparkSession spark = SparkSession.builder()
                .appName("Funnel Analysis")
                .config("mapreduce.app-submission.cross-platform", "true")
                .getOrCreate();

        // conf.set("mapreduce.app-submission.cross-platform", "true");

        Dataset<Row> events = spark.read()
                .option("sep", "\t")
                .option("inferSchema", "true")
                .csv(path)
                .toDF("user_id", "timestamp", "event_id", "event_name", "event_attr", "day");

        Dataset<Row> funnelEvents = events
                .filter(String.format("day between %s and %s", start, end))
                .filter(String.format("event_id in (%s)", mkString(eventIds, ",", "", "")))
                .select("user_id", "timestamp", "event_id");

        funnelEvents.explain();

        List<Row> funnelRows = funnelEvents.collectAsList();
        for (Row row : funnelRows) {
            System.out.println(row);
        }

        spark.udf().register("funnel_count", new FunnelCountUDAF());
        spark.udf().register("funnel_sum", new FunnelSumUDAF());

        Dataset<Row> funnelCounts = funnelEvents
                .groupBy("user_id")
                .agg(expr(String.format("funnel_count(event_id, timestamp, %d,'%s') as cnt",
                        window, mkString(eventIds, ",", "", ""))));

        List<Row> funnelCountsRows = funnelCounts.collectAsList();
        for (Row row : funnelCountsRows) {
            System.out.println(row);
        }

        Row result = funnelCounts
                .agg(expr(String.format("funnel_sum(%d, cnt)", eventIds.length)))
                .first();

        for (Object c : result.getList(0)) {
            System.out.println(c);
        }
    }

    private static String mkString(String[] array, String delimiter, String prefix, String suffix) {
        if (array == null || array.length == 0) {
            return "";
        } else {
            StringJoiner sj = new StringJoiner(delimiter, "", "");
            for (String s : array) {
                sj.add(prefix + s + suffix);
            }
            return sj.toString();
        }
    }

}
