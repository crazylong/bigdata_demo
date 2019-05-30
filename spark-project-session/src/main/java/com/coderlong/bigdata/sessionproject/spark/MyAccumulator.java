package com.coderlong.bigdata.sessionproject.spark;

import com.alibaba.fastjson.JSONObject;
import org.apache.spark.AccumulatorParam;

/**
 * @author Long Qiong
 * @create 2019/5/11
 */
public class MyAccumulator implements AccumulatorParam<JSONObject> {

    @Override
    public JSONObject addAccumulator(JSONObject t1, JSONObject t2) {
        return null;
    }

    @Override
    public JSONObject addInPlace(JSONObject r1, JSONObject r2) {
        return null;
    }

    @Override
    public JSONObject zero(JSONObject initialValue) {
        return null;
    }
}
