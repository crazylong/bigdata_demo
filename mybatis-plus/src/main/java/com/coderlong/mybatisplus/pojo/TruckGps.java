package com.coderlong.mybatisplus.pojo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("MC_TRUCK_GPS")
public class TruckGps {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    //@TableField("TRUCK_CODE")
    private String truckCode;

    
    private Double longitude;

    
    private Double latitude;

    
    private Long direction;

    
    private Long speed;

    
    private String mapclass;

    
    private String mapid;

    
    private String mapnm;

    
    private String remark;

    
    private Date insertTime;

    
    private String terminalCode;

    
}
