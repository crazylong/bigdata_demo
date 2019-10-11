package com.coderlong.bigdata.scala.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 更新分组
 * @author Long Qiong
 * @create 2018/9/18
 */
public class UpdateGroup {
    public static void main(String[] args) {
        System.out.println("aa");
        SparkSession ss = SparkSession
                .builder()
                .appName("updateGroup")
                .config("spark.driver.maxResultSize", "20g")
                //.enableHiveSupport()
                .getOrCreate();

        //ss.sql("SELECT * FROM global_temp.people").show();
        //SQLContext sqlContext =
        Dataset<Row> jdbcDF = ss.read()
                .format("jdbc")
                .option("url", "jdbc:mysql://172.18.1.20:8096")
                .option("dbtable", "jmhx_dev.profile.group")
                .option("user", "jmhx_dev")
                .option("password", "hxjmkkxx")
                .load();
        System.out.println(jdbcDF);
    }
}
