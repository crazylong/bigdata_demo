package com.coderlong.mybatisplus.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.TypeUtils;
import com.cybermkd.mongo.kit.MongoBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point  extends MongoBean {

    @JSONField(ordinal = 0)
    private BigDecimal LONGITUDE;
    @JSONField(ordinal = 1)
    private BigDecimal LATITUDE;

    @Override
    public Map toMap() {
        //避免第一个字母小写
        TypeUtils.compatibleWithJavaBean =true;
        //保证经度在前面
        return JSONObject.parseObject(JSONObject.toJSONString(this), LinkedHashMap.class);
        //return (Map)JSONObject.toJSON(this);
    }
}