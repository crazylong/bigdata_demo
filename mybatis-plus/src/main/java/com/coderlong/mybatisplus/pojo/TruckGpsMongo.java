package com.coderlong.mybatisplus.pojo;


import com.cybermkd.mongo.kit.MongoBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckGpsMongo extends MongoBean  {
    private Long ID;

    private String TRUCK_CODE;

    private Point POINT;
    
    private Long DIRECTION;
    
    private Long SPEED;
    
    private String MAPCLASS;
    
    private String MAPID;
    
    private String MAPNM;
    
    private String REMARK;
    
    private Date INSERT_TIME;
    
    private String TERMINAL_CODE;

    private Long STATUS_ID;

    private Long JOB_ID;

    private String DML;
}
