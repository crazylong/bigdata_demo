package com.coderlong.bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Long Qiong
 * @create 2018/9/13
 */
public class HbaseDemo {
    private Configuration conf = null;
    private Connection    conn = null;
    @Before
    public void init() throws IOException {
        conf = HBaseConfiguration.create();
        //对于hbase的客户端来说，只需要知道hbase所使用的zookeeper集群地址就可以了
        //因为hbase的客户端找hbase读写数据完全不用经过hmaster
        conf.set("hbase.zookeeper.quorum", "cdh02-01,cdh02-02,cdh02-05");
        conn = ConnectionFactory.createConnection(conf);
    }

    @Test
    public void testCreate() throws IOException {
        //获取一个表管理器
        Admin admin = conn.getAdmin();
        //构造一个表描述器，并指定表名
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf("t_user_info2"));

        //构造一个列族描述器，并指定列族名
        HColumnDescriptor hcd1 = new HColumnDescriptor("base_info");
        //为该列族设定一个布隆过滤器类型参数/版本数量
        hcd1.setBloomFilterType(BloomType.ROW).setVersions(1, 3);

        HColumnDescriptor hcd2 = new HColumnDescriptor("extra_info");
        hcd2.setBloomFilterType(BloomType.ROW).setVersions(1, 3);

        htd.addFamily(hcd1).addFamily(hcd2);
        admin.createTable(htd);
        admin.close();
        conn.close();
    }

    /**
     * 插入/修改 数据  DML
     *
     * @throws Exception
     */
    @Test
    public void testPut() throws Exception {

        Table table = conn.getTable(TableName.valueOf("t_user_info2"));

        ArrayList<Put> puts = new ArrayList<Put>();

        // 构建一个put对象（kv），指定其行键
        Put put01 = new Put(Bytes.toBytes("user001"));
        put01.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhangsan"));

        Put put02 = new Put("user001".getBytes());
        put02.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("password"), Bytes.toBytes("123456"));

        Put put03 = new Put("user002".getBytes());
        put03.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("lisi"));
        put03.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put04 = new Put("zhang_sh_01".getBytes());
        put04.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang01"));
        put04.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put05 = new Put("zhang_sh_02".getBytes());
        put05.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang02"));
        put05.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put06 = new Put("liu_sh_01".getBytes());
        put06.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("liu01"));
        put06.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put07 = new Put("zhang_bj_01".getBytes());
        put07.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang03"));
        put07.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));

        Put put08 = new Put("zhang_bj_01".getBytes());
        put08.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("zhang04"));
        put08.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("married"), Bytes.toBytes("false"));




        puts.add(put01);
        puts.add(put02);
        puts.add(put03);
        puts.add(put04);
        puts.add(put05);
        puts.add(put06);
        puts.add(put07);
        puts.add(put08);

        table.put(puts);
        table.close();
        conn.close();

    }


    // 读取数据  ---get：一次读一行
    @Test
    public void testGet() throws Exception {

        Table table = conn.getTable(TableName.valueOf("t_user_info2"));
        // 构造一个get查询参数对象，指定要get的是哪一行
        Get get = new Get("user001".getBytes());
        Result result = table.get(get);
        CellScanner cellScanner = result.cellScanner();
        while (cellScanner.advance()) {
            Cell current = cellScanner.current();
            byte[] familyArray = current.getFamilyArray();
            byte[] qualifierArray = current.getQualifierArray();
            byte[] valueArray = current.getValueArray();

            System.out.println(String.valueOf(familyArray));
            System.out.println(new String(familyArray));

            System.out.print(new String(familyArray, current.getFamilyOffset(), current.getFamilyLength()));
            System.out.print(":" + new String(qualifierArray, current.getQualifierOffset(), current.getQualifierLength()));
            System.out.println(" " + new String(valueArray, current.getValueOffset(), current.getValueLength()));
        }
        table.close();
        conn.close();

    }
}
