package com.coderlong.mybatisplus.pojo;


import com.cybermkd.mongo.kit.MongoBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckGpsMongo extends MongoBean {
    private String GPS_ID;

    private String GPS_TRUCK_CODE;

    
//    private Short longitude;
//
//
//    private Short latitude;
    //DBObject
    private Point point;

    
    private Long GPS_DIRECTION;

    
    private Long GPS_SPEED;

    
    private String GPS_MAPCLASS;

    
    private String GPS_MAPID;

    
    private String GPS_MAPNM;

    
    private String GPS_REMARK;

    
    private Date GPS_INSERT_TIME;

    
    private String GPS_TERMINAL_CODE;
}
