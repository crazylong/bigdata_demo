package com.coderlong.mybatisplus;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coderlong.mybatisplus.mapper.TruckGpsMapper;
import com.coderlong.mybatisplus.pojo.TruckGps;
import com.coderlong.mybatisplus.pojo.Point;
import com.coderlong.mybatisplus.pojo.TruckGpsMongo;
import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.kit.index.MongoIndex;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OracleToMongodb {
    @Resource
    private TruckGpsMapper truckGpsMapper;
    @Test
    public void testSelectById(){
        TruckGps user = this.truckGpsMapper.selectById(3740);
        System.out.println(user);
    }

    @Test
    public void testMongoQuery(){
        MongoClient client = init();
        MongoQuery query=new MongoQuery();
        List<String> truckCodeList = new ArrayList<>();
        truckCodeList.add("FV6");
        truckCodeList.add("8XY");
        System.out.println("start time " + new Date());
        List<JSONObject> jsonObjects = query.use("mc_truck_gps").in("GPS_TRUCK_CODE", truckCodeList).find();
        System.out.println("end time " + new Date());
//        jsonObjects.forEach(json->{
//            System.out.println(json);
//        });
        client.close();
    }


    @Test
    public void testMongoQueryByIndex(){
        MongoClient client = init();
        MongoQuery query=new MongoQuery();
        List<String> truckCodeList = new ArrayList<>();
        truckCodeList.add("3740");
        truckCodeList.add("3741");
        System.out.println("==================start time " + new Date());
        List<JSONObject> jsonObjects = query.use("mc_truck_gps").in("GPS_ID", truckCodeList).find();
        System.out.println("================end time " + new Date());
//        jsonObjects.forEach(json->{
//            System.out.println(json);
//        });
        client.close();
    }


    @Test
    public void testSelectPage() {
        MongoClient client = init();

        for(int i = 1; i <= 1; i++){
            MongoQuery query=new MongoQuery();
            query.use("mc_truck_gps2");
            Page<TruckGps> page = new Page<>(i, 2);
            IPage<TruckGps> truckGpsList = this.truckGpsMapper.selectPage(page, null);
            for(TruckGps truckGps : truckGpsList.getRecords()){
                //Point point = new Point(truckGps.getLongitude(), truckGps.getLatitude());
                //BasicDBObject basicDBObject=BasicDBObject.parse(JSONObject.toJSONString(point));
                TruckGpsMongo gpsMongo = new TruckGpsMongo(truckGps.getId().toString(), truckGps.getTruckCode(), new Point(truckGps.getLongitude(), truckGps.getLatitude()), truckGps.getDirection(),truckGps.getSpeed(),truckGps.getMapclass(),truckGps.getMapid(),truckGps.getMapnm(),truckGps.getRemark(),truckGps.getInsertTime(),truckGps.getTerminalCode());
                query.add(new MongoQuery().set(gpsMongo));
            }
            query.saveList();

        }

        client.close();
//        Page<McTruckGps> page = new Page<>(2, 2);
//        IPage<McTruckGps> userIPage = this.truckGpsMapper.selectPage(page, null);
//        System.out.println("总条数 ------> " + userIPage.getTotal());
//        System.out.println("当前页数 ------> " + userIPage.getCurrent());
//        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
//        List<McTruckGps> records = userIPage.getRecords();
//        for (McTruckGps user : records) {
//            System.out.println(user);
//        }
    }


    @Test
    public void mockGpsData(){
        MongoClient client = init();
        MongoQuery query=new MongoQuery();
        query.use("mc_truck_gps");

        for(int i = 0; i<50000000; i++){
            Double baseLng = 122.0354852042;
            Double baseLat = 29.88762859849;
            //2019-08-01 14:11:02
            Long baseTime = 1564639862L;
            //Point point = new Point(baseLng + i, baseLat + i);
            //BasicDBObject basicDBObject=BasicDBObject.parse(JSONObject.toJSONString(point));

            Date date = new Date(baseTime*1000);

            TruckGpsMongo gpsMongo = new TruckGpsMongo(String.valueOf(i), "3UT" + i%1000, new Point(baseLng + i, baseLat + i), 0L,0L,"mapclass" + i,"mapid" + i,"mapnm" + i,"remark" + i,date,"NPASQ");
            query.add(new MongoQuery().set(gpsMongo));

            if(i%1000==0){
                query.saveList();
                query=new MongoQuery();
                query.use("mc_truck_gps");
            }
        }
        client.close();
    }

    @Test
    public void createIndex(){
        MongoClient client = init();
        MongoIndex index=new MongoIndex("mc_truck_gps");
        //index.ascending("GPS_ID", "GPS_TRUCK_CODE", "GPS_INSERT_TIME").geo2dsphere("point").save();
        //index.ascending("GPS_ID", "GPS_TRUCK_CODE", "GPS_INSERT_TIME").save();
        index.ascending("GPS_ID").save();
        client.close();

    }
    private MongoClient init(){
        MongoPlugin mongoPlugin=new MongoPlugin();
        mongoPlugin.add("192.168.239.3",27017);
        mongoPlugin.setDatabase("mc_gps");
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
        return client;
        //client.close();
    }
}
