package com.coderlong.bigdata.hive.cdh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Long Qiong
 * @create 2018/10/26
 */
public class HiveQuery {
    private static String    driverName = "org.apache.hive.jdbc.HiveDriver";  //驱动名称
    private static String    url = "jdbc:hive2://cdh02-05:10002/";  //由于我的HIVE版本是hive1.1所以使用hive2
    private static String    user = "root";
    private static String    password = "123456";
    private static String    sql = "";
    private static ResultSet res;


    public static void main (String[] args) {
        try {
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement();

            //需要创建的表名
            String tableName = "testHiveTable";


            // 如果表存在就删除
            // sql = "drop table" + tableName;
            // stmt.execute(sql);


            // 创建表
            sql = "CREATE table " + tableName + " (key int, value string)  row format delimited fields terminated by '\t' STORED AS TEXTFILE";
            stmt.execute(sql);


            conn.close();
            conn = null;


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
