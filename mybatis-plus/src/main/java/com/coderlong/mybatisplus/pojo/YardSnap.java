package com.coderlong.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("MC_YARD_SNAP")
public class YardSnap {
    private Long id;
    private Long objectId;
    private Long statusId;
    private Long gpsId;
    private Date insertTime;
    private String terminalCode;
}
