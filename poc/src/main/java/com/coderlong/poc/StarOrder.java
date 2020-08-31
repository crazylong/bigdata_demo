package com.coderlong.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class StarOrder {
    @Resource
    StarOrderConfig starOrderConfig;
    @Resource
    StarOrderService starOrderService;
    @PostConstruct
    public void post(){
        starOrderConfig.getStarOrderList().forEach(starOrderService::createOrder);
    }
}
