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
    private Long ID;
    private String YARD_CODE;

    private String MAPCLASS;


    private String MAPID;


    private String MAPNM;


    private String GRAPH;


    private String COORDINATES;


    private Date INSERT_TIME;

    private String TERMINAL_CODE;

    private Long STATUS_ID;
}