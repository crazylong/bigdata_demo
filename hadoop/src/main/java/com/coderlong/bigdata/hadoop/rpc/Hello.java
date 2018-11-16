package com.coderlong.bigdata.hadoop.rpc;

/**
 * rpc中的协议
 * @author Long Qiong
 * @create 2018/8/13
 */
public interface Hello {
    public static final long versionID=1;
    /**
     * 协议
     * @param words
     * @return
     */
    String say(String words);

}
