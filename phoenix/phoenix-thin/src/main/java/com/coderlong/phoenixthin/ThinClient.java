package com.coderlong.phoenixthin;

import java.sql.*;
import java.util.Random;

public class ThinClient {

    public static void main(String[] args) {
        //getConn();
        ThinClient phoenix = new ThinClient();
        phoenix.upsert3();
        //phoenix.query();
        //phoenixthin.createTable();
        //phoenix.upsert();
    }

    public void upsert3(){
        Connection conn = getConn();

        PreparedStatement pstmt = null;


        try {
            pstmt = conn.prepareStatement("UPSERT INTO LQ.TWELVE_TON4 (date_time,lng,lat,vehicle_no,vehicle_color,encrypt,velocity1,velocity2,mileage,direction,altitude,state31,state30,state29,state28,state27,state26,state25,state24,state23,state22,state21,state20,state19,state18,state17,state16,state15,state14,state13,state12,state11,state10,state09,state08,state07,state06,state05,state04,state03,state02,state01,state00,alarm31,alarm30,alarm29,alarm28,alarm27,alarm26,alarm25,alarm24,alarm23,alarm22,alarm21,alarm20,alarm19,alarm18,alarm17,alarm16,alarm15,alarm14,alarm13,alarm12,alarm11,alarm10,alarm09,alarm08,alarm07,alarm06,alarm05,alarm04,alarm03,alarm02,alarm01,alarm00,update_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

            Long dateTime = 946656000000L;

            for(int i=1;i<=5000000;i++){
                for(int j=1;j<=76;j++){
                    if(j==1 || j==76){
                        String time = String.valueOf(dateTime + i * 1000);
                        pstmt.setString(j,  time);
                    } else {
                        Random random1=new Random();
                        int number=random1.nextInt(51);
                        pstmt.setString(j,  String.valueOf(str.charAt(number)) + i);
                    }


                }
                pstmt.addBatch();

                if(i%2000==0){
                    try {
                        pstmt.executeBatch();
                        conn.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //pstmt.getParameterMetaData().
                        System.out.println(e.getMessage() + i);
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
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

    public void upsert2(){
        Connection conn = getConn();

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPSERT INTO LQ.TEST3 (A,B,C,D,E) VALUES (?,?,?,?,?)");
//            pstmt.setString(1, "a1");
//            pstmt.setString(2, "b1");
//            pstmt.setInt(3, 1);
//            pstmt.setInt(4, 2);
//            pstmt.setInt(5, 3);
//            pstmt.addBatch();
//            pstmt.executeBatch();
            for(int i=2000001;i<5000000;i++){
                pstmt.setString(1, "a" + i);
                pstmt.setString(2, "b" + i);
                pstmt.setInt(3, i);
                pstmt.setInt(4, i+1);
                pstmt.setInt(5, i+2);
                pstmt.addBatch();

                if(i%2000==0){
                    try {
                        pstmt.executeBatch();
                        conn.commit();
                    } catch (Exception e) {
                        System.out.println(i);
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }





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
