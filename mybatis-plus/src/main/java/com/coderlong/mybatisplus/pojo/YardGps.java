package com.coderlong.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("MC_YARD_GPS")
public class YardGps {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    private String yardCode;

    private String mapclass;


    private String mapid;


    private String mapnm;


    private String graph;


    private String coordinates;


    private Date insertTime;

}