package com.coderlong.bigdata.springboot.aop.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CacheModel {
    private String id;
    private Long timeOut;
    private String idFromParamName;

}
