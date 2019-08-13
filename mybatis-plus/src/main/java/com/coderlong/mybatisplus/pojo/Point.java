package com.coderlong.mybatisplus.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.cybermkd.mongo.kit.MongoBean;
import com.cybermkd.mongo.kit.MongoKit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point  extends MongoBean {
    private Double GPS_LONGITUDE;

    private Double GPS_LATITUDE;

    @Override
    public Map toMap() {
        return (Map)JSONObject.toJSON(this);
    }
}