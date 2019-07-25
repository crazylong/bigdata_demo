package com.coderlong.mongodb;

import com.mongodb.MongoClient;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MockData {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        int i = 0;
        List<Document> documents = new ArrayList<Document>();

        NBGGPSINFO nbggpsinfo;
        Field[] fields = NBGGPSINFO.class.getFields();
        while(true){
            Document document = new Document();
            for (Field field : fields) {
                document.append(field.getName(), createRandomCharData(8));
            }
            documents.add(document);
            if(documents.size() == 1000){

            }
        }

    }

    //根据指定长度生成字母和数字的随机数
    //0~9的ASCII为48~57
    //A~Z的ASCII为65~90
    //a~z的ASCII为97~122
    public static String createRandomCharData(int length)
    {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        return sb.toString();
    }

}
