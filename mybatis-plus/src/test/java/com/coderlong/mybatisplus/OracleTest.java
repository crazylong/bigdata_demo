package com.coderlong.mybatisplus;

import com.coderlong.mybatisplus.mapper.TruckGpsMapper;
import com.coderlong.mybatisplus.pojo.TruckGps;
import com.coderlong.mybatisplus.util.Column;
import com.coderlong.mybatisplus.util.OracleUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OracleTest {
    @Resource
    OracleUtil oracleUtil;

    @Resource
    private TruckGpsMapper truckGpsMapper;
    @Test
    public void testSelectById(){
        TruckGps user = this.truckGpsMapper.selectById(3740);
        System.out.println(user);
    }

    @Test
    public void testGetColumn(){
        try {
            List<String> list = oracleUtil.getColumnList("", "MC_TRUCK_GPS");
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
