package com.coderlong.netty.juejin.demo6.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public abstract class Packet6 {
    /**协议版本*/
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**指令*/
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
