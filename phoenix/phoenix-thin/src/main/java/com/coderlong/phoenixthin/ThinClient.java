package com.coderlong.phoenixthin;

import java.sql.*;

public class ThinClient {

    public static void main(String[] args) {
        //getConn();
        ThinClient phoenix = new ThinClient();
        phoenix.query();
        //phoenixthin.createTable();
        //phoenix.upsert();
    }

    public void upsert(){
        Connection conn = getConn();

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPSERT INTO NBGGPSINFO (CAR_NO, GPS_TIME) VALUES (?,?)");
            pstmt.setString(1, "浙A123456");
            pstmt.setString(2, "201907181053");
            pstmt.addBatch();

            pstmt = conn.prepareStatement("UPSERT INTO NBGGPSINFO (CAR_NO, GPS_TIME) VALUES (?,?)");
            pstmt.setString(1, "浙B123456");
            pstmt.setString(2, "201907181101");
            pstmt.addBatch();

            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询phoenix数据
     */
    public void query(){

        Connection conn = getConn();
        ResultSet rs = null;

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("select CAR_NO, GPS_TIME from NBGGPSINFO limit 10");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("symbol:"+rs.getString(1) + ", company:" + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取jdbc连接
     * @return
     */
    public Connection getConn() {
        Connection conn = null;

        try {

           // String driver = "org.apache.phoenixthin.queryserver.client.Driver";
            //String jdbcUrl =  "jdbc:phoenixthin:thin:url=http://hadoop01:8765;serialization=PROTOBUF;";

            String driver="org.apache.phoenix.queryserver.client.Driver";
            String jdbcUrl =  "jdbc:phoenix:thin:url=http://hadoop01:8765;serialization=PROTOBUF;";

            Class.forName(driver);
            conn = DriverManager.getConnection(jdbcUrl, "root", "hzmcdba");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //conn = null;
        return conn;
    }
}
