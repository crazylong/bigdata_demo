package com.coderlong.mybatisplus.pojo;

import com.cybermkd.mongo.kit.MongoBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YardGpsMongo extends MongoBean {
    private Long GPS_ID;
    private String GPS_YARD_CODE;

    private String GPS_MAPCLASS;


    private String GPS_MAPID;


    private String GPS_MAPNM;


    private String GPS_GRAPH;


    private String GPS_COORDINATES;


    private Date GPS_INSERT_TIME;

    private String GPS_TERMINAL_CODE;
}