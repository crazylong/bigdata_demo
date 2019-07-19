package com.coderlong.sparkstreaming.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParse {

    public static Map<String,Object> parseJson(String json){
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(json);
        return jsonMap;
    }

    //用正则表达式进行对重复key进行重命名
    public static String regexMatcher(String needToMatcher,String indexName){
        int count=0;
        String reStr = needToMatcher;
        String tmp  = "";
        String replaceRegexStr = "\"";
        String newIndexName = "";
        newIndexName = indexName.replace(replaceRegexStr, "");
        while(needToMatcher.length()>0){
            Pattern pattern = Pattern.compile(indexName);
            Matcher matcher = pattern.matcher(reStr);
            while(matcher.find()){
                tmp = matcher.replaceFirst("\""+newIndexName+count+"\"");
                reStr = tmp;
                count++;
                break;
            }
            if(!matcher.find()){
                break;
            }
        }
        return reStr;
    }

    //将json特定的nameNode字段内数据外提
    //将json特定的nameNode字段内数据外提
    public static String upColumn(String json,String nameNode){
        Map<Object,Object> jsonMap = (Map<Object, Object>) JSON.parse(json);
        Map<Object,Object> tmpMap = new HashMap<Object, Object>();
        for(Map.Entry<Object, Object> entry:jsonMap.entrySet()){
            tmpMap.put(entry.getKey(), entry.getValue());
            if(nameNode.equals(entry.getKey())){
                String jsonMapstr =  JSON.toJSONString(jsonMap.get(entry.getKey()));
                Map<Object,Object> jsonMap_1 = (Map<Object, Object>) JSON.parse(jsonMapstr);
                for(Map.Entry<Object, Object> entry_1:jsonMap_1.entrySet()){
                    tmpMap.put(entry_1.getKey(), entry_1.getValue());
                }
            }
        }
        tmpMap.remove(nameNode);

        String returnString = JSON.toJSONString(tmpMap);

        return returnString;
    }

    //将json特定的nameNode字段内数据外提  并保留前缀
    public static String upColumnToKeepKey(String json,String nameNode){
        Map<Object,Object> jsonMap = (Map<Object, Object>) JSON.parse(json);
        Map<Object,Object> tmpMap = new HashMap<>();
        String jsonMapstr = "";
        for(Map.Entry<Object, Object> entry:jsonMap.entrySet()){
            tmpMap.put(entry.getKey(), entry.getValue());
            if(nameNode.equals(entry.getKey())){
                jsonMapstr = JSON.toJSONString(jsonMap.get(entry.getKey()));
                jsonMapstr=jsonMapstr.replace("[", "");
                jsonMapstr=jsonMapstr.replace("]", "");
                Map<Object,Object> jsonMap_1 = (Map<Object, Object>) JSON.parse(jsonMapstr);
                for(Map.Entry<Object, Object> entry_1:jsonMap_1.entrySet()){
                    tmpMap.put(entry.getKey()+"_"+entry_1.getKey(), entry_1.getValue());
                }
            }
        }
        tmpMap.remove(nameNode);

        return JSON.toJSONString(tmpMap);
    }
}
