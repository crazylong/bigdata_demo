//package com.jumore.bigdata.profile.entity.po;
//
//import com.jumore.dove.aop.AutoIncrease;
//import com.jumore.dove.aop.Column;
//import com.jumore.dove.aop.Entity;
//import com.jumore.dove.aop.Id;
//import com.jumore.dove.aop.Sequence;
//import com.jumore.dove.aop.Table;
//import java.util.Date;
//
//@Entity
//@Table(name="MC_TRUCK")
//public class Truck {
//    /**null*/
//    @Column(name="ID")
//    @Id
//    @Sequence
//    private Long id;
//
//    /**null*/
//    @Column(name="TRUCK_CODE")
//    private String truckCode;
//
//    /**null*/
//    @Column(name="TRUCK_LICENSE")
//    private String truckLicense;
//
//    /**null*/
//    @Column(name="TRUCK_RFID")
//    private String truckRfid;
//
//    /**null*/
//    @Column(name="TRUCK_COMPANY")
//    private String truckCompany;
//
//    /**null*/
//    @Column(name="TRUCK_TYPE")
//    private String truckType;
//
//    /**null*/
//    @Column(name="TRUCK_PRO_COMPANY")
//    private String truckProCompany;
//
//    /**null*/
//    @Column(name="TRUCK_PRO_DATE")
//    private Date truckProDate;
//
//    /**null*/
//    @Column(name="TRUCK_DANGEROUS_FLAG")
//    private String truckDangerousFlag;
//
//    /**null*/
//    @Column(name="TRUCK_GPS_FLAG")
//    private String truckGpsFlag;
//
//    /**null*/
//    @Column(name="TRUCK_MONITOR_FLAG")
//    private String truckMonitorFlag;
//
//    /**null*/
//    @Column(name="TRUCK_NW")
//    private String truckNw;
//
//    /**null*/
//    @Column(name="TRUCK_WORK_TYPE")
//    private String truckWorkType;
//
//    /**null*/
//    @Column(name="TRUCK_OUTSIDE_FLAG")
//    private String truckOutsideFlag;
//
//    /**null*/
//    @Column(name="TRUCK_SUPER_VISION")
//    private String truckSuperVision;
//
//    /**null*/
//    @Column(name="TRUCK_VFIRST_TYPE")
//    private String truckVfirstType;
//
//    /**null*/
//    @Column(name="TRUCK_TRAILER_TYPE")
//    private String truckTrailerType;
//
//    /**null*/
//    @Column(name="TRUCK_ENGINE_NO")
//    private String truckEngineNo;
//
//    /**null*/
//    @Column(name="TRUCK_UNDERPAN_NO")
//    private String truckUnderpanNo;
//
//    /**null*/
//    @Column(name="TRUCK_NAVI_CERT")
//    private String truckNaviCert;
//
//    /**null*/
//    @Column(name="TRUCK_SUR_TAX")
//    private String truckSurTax;
//
//    /**null*/
//    @Column(name="TRUCK_BUY_DATE")
//    private Date truckBuyDate;
//
//    /**null*/
//    @Column(name="TRUCK_REG_DATE")
//    private Date truckRegDate;
//
//    /**null*/
//    @Column(name="TRUCK_JOIN_DATE")
//    private Date truckJoinDate;
//
//    /**null*/
//    @Column(name="TRUCK_STATUS")
//    private String truckStatus;
//
//    /**null*/
//    @Column(name="TRUCK_ENABLE_FLAG")
//    private String truckEnableFlag;
//
//    /**null*/
//    @Column(name="TRUCK_CONTACT")
//    private String truckContact;
//
//    /**null*/
//    @Column(name="TRUCK_TEL")
//    private String truckTel;
//
//    /**null*/
//    @Column(name="TRUCK_REMARK")
//    private String truckRemark;
//
//    /**null*/
//    @Column(name="TRUCK_NET")
//    private Short truckNet;
//
//    /**null*/
//    @Column(name="TRUCK_POWER")
//    private String truckPower;
//
//    /**null*/
//    @Column(name="TRUCK_INSPECTION_DATE")
//    private Date truckInspectionDate;
//
//    /**null*/
//    @Column(name="TRUCK_EMPTYCTN_FLAG")
//    private String truckEmptyctnFlag;
//
//    /**null*/
//    @Column(name="TRUCK_RFID_TYPE")
//    private String truckRfidType;
//
//    /**null*/
//    @Column(name="TRUCK_IS_CUSTOM")
//    private String truckIsCustom;
//
//    /**null*/
//    @Column(name="ACTIVE_FLAG")
//    private String activeFlag;
//
//    /**null*/
//    @Column(name="INSERT_TIME")
//    private Date insertTime;
//
//    /**null*/
//    @Column(name="TRUCK_NO")
//    private String truckNo;
//
//    /**null*/
//    @Column(name="TERMINAL_CODE")
//    private String terminalCode;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTruckCode() {
//        return truckCode;
//    }
//
//    public void setTruckCode(String truckCode) {
//        this.truckCode = truckCode == null ? null : truckCode.trim();
//    }
//
//    public String getTruckLicense() {
//        return truckLicense;
//    }
//
//    public void setTruckLicense(String truckLicense) {
//        this.truckLicense = truckLicense == null ? null : truckLicense.trim();
//    }
//
//    public String getTruckRfid() {
//        return truckRfid;
//    }
//
//    public void setTruckRfid(String truckRfid) {
//        this.truckRfid = truckRfid == null ? null : truckRfid.trim();
//    }
//
//    public String getTruckCompany() {
//        return truckCompany;
//    }
//
//    public void setTruckCompany(String truckCompany) {
//        this.truckCompany = truckCompany == null ? null : truckCompany.trim();
//    }
//
//    public String getTruckType() {
//        return truckType;
//    }
//
//    public void setTruckType(String truckType) {
//        this.truckType = truckType == null ? null : truckType.trim();
//    }
//
//    public String getTruckProCompany() {
//        return truckProCompany;
//    }
//
//    public void setTruckProCompany(String truckProCompany) {
//        this.truckProCompany = truckProCompany == null ? null : truckProCompany.trim();
//    }
//
//    public Date getTruckProDate() {
//        return truckProDate;
//    }
//
//    public void setTruckProDate(Date truckProDate) {
//        this.truckProDate = truckProDate;
//    }
//
//    public String getTruckDangerousFlag() {
//        return truckDangerousFlag;
//    }
//
//    public void setTruckDangerousFlag(String truckDangerousFlag) {
//        this.truckDangerousFlag = truckDangerousFlag == null ? null : truckDangerousFlag.trim();
//    }
//
//    public String getTruckGpsFlag() {
//        return truckGpsFlag;
//    }
//
//    public void setTruckGpsFlag(String truckGpsFlag) {
//        this.truckGpsFlag = truckGpsFlag == null ? null : truckGpsFlag.trim();
//    }
//
//    public String getTruckMonitorFlag() {
//        return truckMonitorFlag;
//    }
//
//    public void setTruckMonitorFlag(String truckMonitorFlag) {
//        this.truckMonitorFlag = truckMonitorFlag == null ? null : truckMonitorFlag.trim();
//    }
//
//    public String getTruckNw() {
//        return truckNw;
//    }
//
//    public void setTruckNw(String truckNw) {
//        this.truckNw = truckNw == null ? null : truckNw.trim();
//    }
//
//    public String getTruckWorkType() {
//        return truckWorkType;
//    }
//
//    public void setTruckWorkType(String truckWorkType) {
//        this.truckWorkType = truckWorkType == null ? null : truckWorkType.trim();
//    }
//
//    public String getTruckOutsideFlag() {
//        return truckOutsideFlag;
//    }
//
//    public void setTruckOutsideFlag(String truckOutsideFlag) {
//        this.truckOutsideFlag = truckOutsideFlag == null ? null : truckOutsideFlag.trim();
//    }
//
//    public String getTruckSuperVision() {
//        return truckSuperVision;
//    }
//
//    public void setTruckSuperVision(String truckSuperVision) {
//        this.truckSuperVision = truckSuperVision == null ? null : truckSuperVision.trim();
//    }
//
//    public String getTruckVfirstType() {
//        return truckVfirstType;
//    }
//
//    public void setTruckVfirstType(String truckVfirstType) {
//        this.truckVfirstType = truckVfirstType == null ? null : truckVfirstType.trim();
//    }
//
//    public String getTruckTrailerType() {
//        return truckTrailerType;
//    }
//
//    public void setTruckTrailerType(String truckTrailerType) {
//        this.truckTrailerType = truckTrailerType == null ? null : truckTrailerType.trim();
//    }
//
//    public String getTruckEngineNo() {
//        return truckEngineNo;
//    }
//
//    public void setTruckEngineNo(String truckEngineNo) {
//        this.truckEngineNo = truckEngineNo == null ? null : truckEngineNo.trim();
//    }
//
//    public String getTruckUnderpanNo() {
//        return truckUnderpanNo;
//    }
//
//    public void setTruckUnderpanNo(String truckUnderpanNo) {
//        this.truckUnderpanNo = truckUnderpanNo == null ? null : truckUnderpanNo.trim();
//    }
//
//    public String getTruckNaviCert() {
//        return truckNaviCert;
//    }
//
//    public void setTruckNaviCert(String truckNaviCert) {
//        this.truckNaviCert = truckNaviCert == null ? null : truckNaviCert.trim();
//    }
//
//    public String getTruckSurTax() {
//        return truckSurTax;
//    }
//
//    public void setTruckSurTax(String truckSurTax) {
//        this.truckSurTax = truckSurTax == null ? null : truckSurTax.trim();
//    }
//
//    public Date getTruckBuyDate() {
//        return truckBuyDate;
//    }
//
//    public void setTruckBuyDate(Date truckBuyDate) {
//        this.truckBuyDate = truckBuyDate;
//    }
//
//    public Date getTruckRegDate() {
//        return truckRegDate;
//    }
//
//    public void setTruckRegDate(Date truckRegDate) {
//        this.truckRegDate = truckRegDate;
//    }
//
//    public Date getTruckJoinDate() {
//        return truckJoinDate;
//    }
//
//    public void setTruckJoinDate(Date truckJoinDate) {
//        this.truckJoinDate = truckJoinDate;
//    }
//
//    public String getTruckStatus() {
//        return truckStatus;
//    }
//
//    public void setTruckStatus(String truckStatus) {
//        this.truckStatus = truckStatus == null ? null : truckStatus.trim();
//    }
//
//    public String getTruckEnableFlag() {
//        return truckEnableFlag;
//    }
//
//    public void setTruckEnableFlag(String truckEnableFlag) {
//        this.truckEnableFlag = truckEnableFlag == null ? null : truckEnableFlag.trim();
//    }
//
//    public String getTruckContact() {
//        return truckContact;
//    }
//
//    public void setTruckContact(String truckContact) {
//        this.truckContact = truckContact == null ? null : truckContact.trim();
//    }
//
//    public String getTruckTel() {
//        return truckTel;
//    }
//
//    public void setTruckTel(String truckTel) {
//        this.truckTel = truckTel == null ? null : truckTel.trim();
//    }
//
//    public String getTruckRemark() {
//        return truckRemark;
//    }
//
//    public void setTruckRemark(String truckRemark) {
//        this.truckRemark = truckRemark == null ? null : truckRemark.trim();
//    }
//
//    public Short getTruckNet() {
//        return truckNet;
//    }
//
//    public void setTruckNet(Short truckNet) {
//        this.truckNet = truckNet;
//    }
//
//    public String getTruckPower() {
//        return truckPower;
//    }
//
//    public void setTruckPower(String truckPower) {
//        this.truckPower = truckPower == null ? null : truckPower.trim();
//    }
//
//    public Date getTruckInspectionDate() {
//        return truckInspectionDate;
//    }
//
//    public void setTruckInspectionDate(Date truckInspectionDate) {
//        this.truckInspectionDate = truckInspectionDate;
//    }
//
//    public String getTruckEmptyctnFlag() {
//        return truckEmptyctnFlag;
//    }
//
//    public void setTruckEmptyctnFlag(String truckEmptyctnFlag) {
//        this.truckEmptyctnFlag = truckEmptyctnFlag == null ? null : truckEmptyctnFlag.trim();
//    }
//
//    public String getTruckRfidType() {
//        return truckRfidType;
//    }
//
//    public void setTruckRfidType(String truckRfidType) {
//        this.truckRfidType = truckRfidType == null ? null : truckRfidType.trim();
//    }
//
//    public String getTruckIsCustom() {
//        return truckIsCustom;
//    }
//
//    public void setTruckIsCustom(String truckIsCustom) {
//        this.truckIsCustom = truckIsCustom == null ? null : truckIsCustom.trim();
//    }
//
//    public String getActiveFlag() {
//        return activeFlag;
//    }
//
//    public void setActiveFlag(String activeFlag) {
//        this.activeFlag = activeFlag == null ? null : activeFlag.trim();
//    }
//
//    public Date getInsertTime() {
//        return insertTime;
//    }
//
//    public void setInsertTime(Date insertTime) {
//        this.insertTime = insertTime;
//    }
//
//    public String getTruckNo() {
//        return truckNo;
//    }
//
//    public void setTruckNo(String truckNo) {
//        this.truckNo = truckNo == null ? null : truckNo.trim();
//    }
//
//    public String getTerminalCode() {
//        return terminalCode;
//    }
//
//    public void setTerminalCode(String terminalCode) {
//        this.terminalCode = terminalCode == null ? null : terminalCode.trim();
//    }
//}