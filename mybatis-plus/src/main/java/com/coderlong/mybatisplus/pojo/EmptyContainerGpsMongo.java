package com.coderlong.mybatisplus.pojo;


import com.cybermkd.mongo.kit.MongoBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmptyContainerGpsMongo extends MongoBean {
    private Long ID;
    
    private String EQUIPMENT_CODE;

    private Point POINT;

    private Long DIRECTION;

    
    private Long SPEED;

    
    private String MAPCLASS;

    
    private String MAPID;

    
    private String MAPNM;

    
    private Date INSERT_TIME;

    
    private Long HEIGHT;

    
    private String TERMINAL_CODE;
}