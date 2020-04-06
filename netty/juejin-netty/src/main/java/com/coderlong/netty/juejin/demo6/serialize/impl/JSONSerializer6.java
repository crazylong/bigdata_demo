package com.coderlong.netty.juejin.demo6.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.coderlong.netty.juejin.demo6.serialize.Serializer6;
import com.coderlong.netty.juejin.demo6.serialize.SerializerAlgorithm6;

public class JSONSerializer6 implements Serializer6 {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm6.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
