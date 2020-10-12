package com.coderlong.mongodb.plugin;

import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PortVisionGps {
    public static void main(String[] args) {
        init();
        query();
    }

    private static void query(){
        MongoQuery query=new MongoQuery();
        List<String> codeList = new ArrayList<>();
        codeList.add("T014");
        List<JSONObject> colList = query.use("MC_TRUCK_GPS")
                .in("TRUCK_CODE", codeList)
                .gte("INSERT_TIME", new Date(1505750403000L))
                .lte("INSERT_TIME", new Date(1505750406000L))
                .find();
        System.out.println(colList.size());
        System.out.println(colList);
    }

    private static MongoClient init(){
        MongoPlugin mongoPlugin=new MongoPlugin();
        mongoPlugin.add("192.168.239.2",27017);
        mongoPlugin.setDatabase("mc_gps");
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
        return client;
    }
}
