package com.coderlong.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("MC_TRUCK")
public class Truck {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;


    private String truckCode;


    private String truckLicense;


    private String truckRfid;


    private String truckCompany;


    private String truckType;


    private String truckProCompany;


    private Date truckProDate;


    private String truckDangerousFlag;


    private String truckGpsFlag;


    private String truckMonitorFlag;


    private String truckNw;


    private String truckWorkType;


    private String truckOutsideFlag;


    private String truckSuperVision;


    private String truckVfirstType;


    private String truckTrailerType;


    private String truckEngineNo;


    private String truckUnderpanNo;


    private String truckNaviCert;


    private String truckSurTax;


    private Date truckBuyDate;


    private Date truckRegDate;


    private Date truckJoinDate;


    private String truckStatus;


    private String truckEnableFlag;


    private String truckContact;


    private String truckTel;


    private String truckRemark;


    private Short truckNet;


    private String truckPower;


    private Date truckInspectionDate;


    private String truckEmptyctnFlag;


    private String truckRfidType;


    private String truckIsCustom;


    private String activeFlag;


    private Date insertTime;


    private String truckNo;


    private String terminalCode;
}