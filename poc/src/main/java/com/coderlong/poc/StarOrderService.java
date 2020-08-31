package com.coderlong.poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class StarOrderService {
    @Async
    public void createOrder(StarOrderConfig.StarOrderEntity starOrderEntity){
        log.info("startStar:" + new Date());


        String[] buyNumArr = starOrderEntity.getBuyNum().split(",");

        for(String buyNum : buyNumArr){
            JSONObject paramNum = new JSONObject();
            paramNum.put("secondPassword", starOrderEntity.getSecondPassword());
            paramNum.put("buyNum", buyNum);

            JSONObject result = new JSONObject();
            try {
                result = HttpUtil.post2(starOrderEntity.getCookie(), starOrderEntity.getBfcToken(), JSON.toJSONString(paramNum));
                log.warn("result=" + JSONObject.toJSONString(result));
            } catch (Exception e) {
                e.printStackTrace();
                log.warn(new Date() + "starOrderEntity=" + starOrderEntity);
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
