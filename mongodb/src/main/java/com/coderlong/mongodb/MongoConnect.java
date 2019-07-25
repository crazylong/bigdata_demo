package com.coderlong.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoConnect {
    public static void main(String[] args) {

    }

    public void insert(MongoCollection<Document> collection){
        Document document = new Document("title", "MongoDB").
                append("description", "database").
                append("likes", 100).
                append("by", "Fly");
        collection.insertOne(document);


        List<Document> documents = new ArrayList<Document>();

        Document document2 = new Document("title", "hbase").
                append("description", "hdfs").
                append("likes", 50).
                append("by", "小白");
        documents.add(document2);

        documents.add(new Document("title", "neo4j").
                append("description", "图计算库").
                append("likes", 120).
                append("by", "Sky"));

        collection.insertMany(documents);
    }

    /**
     * 删除文档
     * @param collection
     */
    public void delete(MongoCollection<Document> collection){
        collection.deleteOne(Filters.eq("likes", 200));
        collection.deleteMany(Filters.eq("likes", 200));
    }

    public void update(MongoCollection<Document> collection){
        collection.updateMany(Filters.eq("likes", 100),
                    new Document("$set", new Document("likes",200)));
    }

    public void findAll(MongoCollection<Document> collection){
        FindIterable<Document> docs =  collection.find();
        MongoCursor<Document> mongoCursor = docs.iterator();
        while (mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    /**
     * data definition language
     */
    public void ddl(){
        MongoClient mongoClient = getConn();

        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        System.out.println("mongoDatabase=====>" + mongoDatabase.getName());

        //创建集合
        //mongoDatabase.createCollection("newColl");

        //获取所有集合名称
        MongoIterable<String> collNames = mongoDatabase.listCollectionNames();
        for(String name : collNames){
            System.out.println(name);
        }

        //获取所有集合
        ListCollectionsIterable<Document> colList = mongoDatabase.listCollections();

        //获取某个集合
        MongoCollection<Document> newColl = mongoDatabase.getCollection("newColl");
    }

    private static MongoClient getConn(){
        return new MongoClient("localhost", 27017);
    }
}
