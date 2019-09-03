package com.coderlong.mybatisplus.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("MC_REACHSTACKER_GPS")
public class ReachStackerGps {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    private String equipmentCode;

    private BigDecimal longitude;


    private BigDecimal latitude;


    private Long direction;


    private Long speed;


    private Long height;


    private String mapclass;


    private String mapid;

    private String mapnm;


    private Date insertTime;

    private String terminalCode;
}