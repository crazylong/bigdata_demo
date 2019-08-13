package com.coderlong.mongodb.plugin;

import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mongodb.MongoClient;

import java.util.List;

public class Demo {
    public static void main(String[] args) {

        init();
        query();

    }

    private static void insertGis(){

    }


    private static void query(){
        MongoQuery query=new MongoQuery();
        List<JSONObject> colList = query.use("col").byId("5d157092d89a0500591a4692").find();
        System.out.println(colList);
    }

    private static void init(){
        MongoPlugin mongoPlugin=new MongoPlugin();
        mongoPlugin.add("127.0.0.1",27017);
        mongoPlugin.setDatabase("geo");
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
        client.close();
    }
}
