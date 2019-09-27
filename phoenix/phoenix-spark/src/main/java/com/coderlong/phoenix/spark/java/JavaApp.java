package com.coderlong.phoenix.spark.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.joda.time.DateTime;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JavaApp {
    /**
     * phoenix jdbc config
     */
    private static final String DB_PHOENIX_DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver";
    private static final String DB_PHOENIX_URL = "jdbc:phoenix:hadoop01,hadoop02,hadoop03";
    private static final String DB_PHOENIX_USER = "";
    private static final String DB_PHOENIX_PASS = "";
    private static final String DB_PHOENIX_FETCHSIZE = "10000";
    /**
     * 加载数据查询SQL
     */
    private static final String SQL_QUERY = "(SELECT date,member_id FROM lq.events WHERE time>='%s' AND time<'%s' AND event='login') events";
    /**
     * 任务名称
     */
    private static final String APP_NAME = "Test";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(APP_NAME)
                .setMaster("local[1]")
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .registerKryoClasses(
                        new Class[]{}
                );
        SparkSession sparkSession = SparkSession.builder().config(conf).getOrCreate();
        DateTime start = new DateTime(args[0]), end = new DateTime(args[1]);
        String sql = String.format(SQL_QUERY, start.toString("yyyy-MM-dd"), end.toString("yyyy-MM-dd"));

        // 正常去重后拼接字符串保存与后边测试binary做对比
        sinkDataByVarchar(sparkSession, sql);

        sparkSession.stop();
    }
    /**
     * 普通方式去重并存储
     *
     * @param sparkSession
     * @param query
     * @return
     */
    private static void sinkDataByVarchar(SparkSession sparkSession, String query) {
        try {
            // JDBC连接属性
            Properties connProp = new Properties();
            connProp.put("driver", DB_PHOENIX_DRIVER);
            connProp.put("user", DB_PHOENIX_USER);
            connProp.put("password", DB_PHOENIX_PASS);
            connProp.put("fetchsize", DB_PHOENIX_FETCHSIZE);
            connProp.put("phoenix.schema.isNamespaceMappingEnabled", "true");

            JavaRDD<Row> rows = sparkSession
                    .read()
                    .jdbc(DB_PHOENIX_URL, query, connProp)
                    .filter("member_id != -1")
                    .javaRDD()
                    .mapToPair(r -> new Tuple2<>(
                            r.getString(0)
                            , r.getLong(1)
                    ))
                    .distinct()
                    .groupByKey()
                    .map(r -> {
                        StringBuffer sb = new StringBuffer();
                        r._2.forEach(v -> {
                            sb.append(v);
                        });

                        return RowFactory.create(r._1, sb.toString());
                    });

            //rows.s
            rows.foreach(row->{
                System.out.println(row.getString(0) + "_" + row.getString(1));
            });
            // schema
            List<StructField> fields = new ArrayList<>();
            fields.add(DataTypes.createStructField("date", DataTypes.StringType, false));
            fields.add(DataTypes.createStructField("dist_mem", DataTypes.StringType, true));
            StructType schema = DataTypes.createStructType(fields);
            // 写入
            String insertTable = "lq.test_string";
            sparkSession
                    .createDataFrame(rows, schema)
                    .write()
                    .format("org.apache.phoenix.spark")
                    .mode(SaveMode.Overwrite)
                    .option("table", insertTable)
                    .option("zkUrl", DB_PHOENIX_URL)
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
