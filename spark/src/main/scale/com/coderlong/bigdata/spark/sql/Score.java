package com.coderlong.bigdata.spark.sql;

/**
  *
  * @author Long Qiong 
  * @create 2018/10/18
  */
public class Score {
  public String name;

  public String metric;

  public String value;

  public String timestamp;

  public String deviceTypeId;

  public String deviceNo;

  public String deviceId;

  public String gatewayId;
  public Score(String name, String metric, String value, String timestamp, String deviceTypeId, String deviceNo, String deviceId, String gatewayId){
    this.name=name;
    this.metric=metric;
    this.value=value;
    this.timestamp=timestamp;
    this.deviceTypeId=deviceTypeId;
    this.deviceNo=deviceNo;
    this.deviceId=deviceId;
    this.gatewayId=gatewayId;
  }
  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getDeviceTypeId() {
    return deviceTypeId;
  }

  public void setDeviceTypeId(String deviceTypeId) {
    this.deviceTypeId = deviceTypeId;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getGatewayId() {
    return gatewayId;
  }

  public void setGatewayId(String gatewayId) {
    this.gatewayId = gatewayId;
  }
}
