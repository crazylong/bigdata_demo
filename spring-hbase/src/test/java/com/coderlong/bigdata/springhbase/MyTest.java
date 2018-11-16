package com.coderlong.bigdata.springhbase;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.HbaseUtils;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Long Qiong
 * @create 2018/10/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class MyTest {
    @Autowired
    private HbaseTemplate template;

    @Test
    public void testFind() {
        List<String> rows = template.find("t_user_info", "base_info", "age", new RowMapper<String>() {
            public String mapRow(Result result, int i) throws Exception {
                return result.toString();
            }
        });
        Assert.assertNotNull(rows);
    }

    @Test
    public void testPut() {
        template.getTableFactory();
//        HbaseUtils.getHTable();
//        HbaseUtils.
                //template.getConfiguration();
       // template.setConfiguration();
        template.put("t_user_info", "liu-20-001", "base_info", "name", Bytes.toBytes("Alice"));
    }
}
