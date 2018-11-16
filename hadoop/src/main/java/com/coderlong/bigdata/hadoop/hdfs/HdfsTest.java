package com.coderlong.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * 用java代码操作hdfs文件系统
 * @author Long Qiong
 * @create 2018/8/12
 */
public class HdfsTest {
    public static void main(String[] args) throws IOException {
        readFileToConsol("");
    }

    //读取hdfs文件系统中的文件
    public static void readFileToConsol(String path) throws IOException {
        //获取配置
        Configuration conf = new Configuration();

        //配置
        conf.set("fs.defaultFS", "hdfs://192.168.153.141:9000");

        //获取hdfs文件系统的操作对象
        FileSystem fs = FileSystem.get(conf);

        //具体对文件的操作
       FSDataInputStream fis = fs.open(new Path("/README.txt"));
        IOUtils.copyBytes(fis,System.out, 4096, true);
    }

    //读取hdfs文件系统中的文件
    @Test
    public void readFileToLocal() throws IOException, URISyntaxException, InterruptedException {
        //获取配置
        Configuration conf = new Configuration();

        //获取hdfs文件系统的操作对象
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.153.141:9000"), conf, "root");
        //具体对文件的操作
        FSDataInputStream fis = fs.open(new Path("/README.txt"));

        OutputStream out = new FileOutputStream(new File("E:\\books\\大数据\\temp\\test.txt"));
        IOUtils.copyBytes(fis,out, 4096, true);
    }


    @Test
    public void copyFromLocal() throws IOException, URISyntaxException, InterruptedException {
        //获取配置
        Configuration conf = new Configuration();

        //获取hdfs文件系统的操作对象

        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.153.141:9000"), conf, "root");
        //具体对文件的操作
        fs.copyFromLocalFile(new Path("E:\\books\\大数据\\temp\\123.txt"), new Path("/test/123"));
        System.out.println("finished.....");
    }

    @Test
    public void test(){
//        if(5!=6 && 1!=2){
//            System.out.println(1);
//        } else {
//            System.out.println(2);
//        }

        Integer a = new Integer(6);
        Integer a1 = new Integer(6);
//        if(a==a1){
//            System.out.println("a=a1");
//        } else {
//            System.out.println("a!=a1");
//        }


        if(Objects.equals(a, a1)){
            System.out.println("a=a1");
        } else {
            System.out.println("a!=a1");
        }

//        Integer a2 = null;
//        System.out.println(a2.intValue());
    }
}
