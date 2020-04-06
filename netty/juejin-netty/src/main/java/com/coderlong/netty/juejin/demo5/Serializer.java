package com.coderlong.netty.juejin.demo5;

public interface Serializer {
    /**json 序列化*/
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**序列号算法*/
    byte getSerializerAlgorithm();

    /**java对象转换成二进制*/
    byte[] serialize(Object object);

    /**二进制转换成java对象*/
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
