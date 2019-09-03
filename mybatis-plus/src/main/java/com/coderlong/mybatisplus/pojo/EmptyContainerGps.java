package com.coderlong.mybatisplus.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@TableName("mc_EmptyContainer_Gps")
public class EmptyContainerGps {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    
    private String equipmentCode;

    private BigDecimal longitude;


    private BigDecimal latitude;

    private Long direction;

    
    private Long speed;

    
    private String mapclass;

    
    private String mapid;

    
    private String mapnm;

    
    private Date insertTime;

    
    private Long height;

    
    private String terminalCode;
}