package com.coderlong.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("MC_BRIDGECRANE_SNAP")
public class BridgeCraneSnap {
    private Long id;
    private Long objectId;
    private Long statusId;
    private Long gpsId;
    private Date insertTime;
    private Long jobId;
    private String dml;
    private String terminalCode;
}
