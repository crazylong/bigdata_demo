package com.coderlong.poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Slf4j
public class StarOrder {
    //List<String, Map<Integer, String>> parmas = new ArrayList<String, HashMap<Integer, String>>();
    //List<String, Map<Integer, String>> parmas = new ArrayList<>();

    private static  String passwd = "812876";
    private static String[] cookie1 = new String[]{"",
            "",
            ""};



    @PostConstruct
    public void post(){
        Map<String, String> cookie_bfc_token = new HashMap<>();
        cookie_bfc_token.put("__cfduid=d414b3b0e13b036c5edbb16c13ff95bb61597712695; token=Bearer%20eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweDA5YWUxNGRkZDcxYTMwNTMzNTcwZWZlMmM2M2FmOTRhYjJmZGE0YTYiLCJleHAiOjE1OTg0ODk1MDEsImlhdCI6MTU5Nzg4NDcwMX0.3LsO5-DWFl6Wkz0bvaqaY8gk3aVSo2zj1WtaS9KHit17BK-E2gDXW9GBpckRT05OTRDsScs_olj4mzkWWMlyFg", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweDA5YWUxNGRkZDcxYTMwNTMzNTcwZWZlMmM2M2FmOTRhYjJmZGE0YTYiLCJleHAiOjE1OTg0ODk1MDEsImlhdCI6MTU5Nzg4NDcwMX0.3LsO5-DWFl6Wkz0bvaqaY8gk3aVSo2zj1WtaS9KHit17BK-E2gDXW9GBpckRT05OTRDsScs_olj4mzkWWMlyFg");
        cookie_bfc_token.put("__cfduid=d69c1de8ae82993516f95c1f335dd40561597850427; token=Bearer%20eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweDFjYjc0NGY2OGM3NjJmYTQ0ZDE2ZTVkMGY5OWY3YTdlNWJlZWQ0ZDAiLCJleHAiOjE1OTg0OTA0NzIsImlhdCI6MTU5Nzg4NTY3Mn0.t508D2mdaV4IRqBUNXe4k6YofJUDrg3jOzgkFmbb4hg48vvNE2_3gbqSwFxGDf82FST5CKaKGgDKbEEHP9Hp5Q", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweDFjYjc0NGY2OGM3NjJmYTQ0ZDE2ZTVkMGY5OWY3YTdlNWJlZWQ0ZDAiLCJleHAiOjE1OTg0OTA0NzIsImlhdCI6MTU5Nzg4NTY3Mn0.t508D2mdaV4IRqBUNXe4k6YofJUDrg3jOzgkFmbb4hg48vvNE2_3gbqSwFxGDf82FST5CKaKGgDKbEEHP9Hp5Q");
        cookie_bfc_token.put("__cfduid=d156d6c294acfd8b1bce0cc0812f8fa991597717180; token=Bearer%20eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweGQyYjliNDM5N2I0MzcwMTQwM2U0NDhkYzQ5NWRkNjIyMDA0MDIwYjUiLCJleHAiOjE1OTg0OTA1NTgsImlhdCI6MTU5Nzg4NTc1OH0.7fEnQIUo2z6xpIZOWJrgd222txDYSge2DdRjo2tmvA5RDXpxWbl49GxBAwI7tGy76AJA2OcAxekBLSP36QPcEg", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweGQyYjliNDM5N2I0MzcwMTQwM2U0NDhkYzQ5NWRkNjIyMDA0MDIwYjUiLCJleHAiOjE1OTg0OTA1NTgsImlhdCI6MTU5Nzg4NTc1OH0.7fEnQIUo2z6xpIZOWJrgd222txDYSge2DdRjo2tmvA5RDXpxWbl49GxBAwI7tGy76AJA2OcAxekBLSP36QPcEg");

        JSONObject paramNum1 = new JSONObject();
        paramNum1.put("secondPassword", passwd);
        paramNum1.put("buyNum", "100");

//        cookie_bfc_token.forEach((k, v)->{
//            createOrder(k, v, paramNum1);
//            createOrder(k, v, paramNum1);
//        });

        while (true){
            if(System.currentTimeMillis()==1597890600000L){
                cookie_bfc_token.forEach((k, v)->{
                    createOrder(k, v, paramNum1);
                    createOrder(k, v, paramNum1);
                    createOrder(k, v, paramNum1);
                });
            }
        }

    }

    @Async
    public void createOrder(String cookie, String bfc_token, JSONObject param){
        log.info("start:" + new Date());

            JSONObject result = new JSONObject();
            try {
                result = HttpUtil.post(cookie, bfc_token, JSON.toJSONString(param));
            } catch (Exception e) {
                e.printStackTrace();

            }

            log.info(new Date() + "-->cookie=" + cookie + ",result=" + result.toJSONString());


    }
}
