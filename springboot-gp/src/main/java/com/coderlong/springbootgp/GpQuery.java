package com.coderlong.springbootgp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class GpQuery {
    public static void main(String[] args) {
        SpringApplication.run(GpQuery.class, args);
        try{
            Class.forName("com.pivotal.jdbc.GreenplumDriver");
            Connection connection = DriverManager.getConnection("jdbc:pivotal:greenplum://192.168.242.46:5432;DatabaseName=lq", "gpadmin", "hzmcdba");

            String sql = "select  * from customer";
            try(connection){
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet resultSet = ps.executeQuery();
                List<Map<String, Object>> list = rsToMapList(resultSet);
                System.out.println(list);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static List<Map<String, Object>> rsToMapList(ResultSet rs) throws Exception {
        List<Map<String,Object>> list=new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount=rsmd.getColumnCount();
        List<String> colNameList=new ArrayList<>();
        for(int i=0;i<colCount;i++){
            colNameList.add(rsmd.getColumnName(i+1));
        }

        while(rs.next()){
            Map<String, Object> map=new HashMap<>();
            for(int i=0;i<colCount;i++){

                String key=colNameList.get(i);
                Object value=rs.getString(colNameList.get(i));
                map.put(key, value);
            }
            list.add(map);
        }
        return list;
    }
}
