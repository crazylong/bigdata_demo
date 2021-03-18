package com.coderlong.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 直接用map接收数据
 *
 * @author Jiaju Zhuang
 */
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoModelDataListener.class);

    private JdbcTemplate jdbcTemplate;
    private String fileName;
    private String tableName;

    public NoModelDataListener(){}
    public NoModelDataListener(JdbcTemplate jdbcTemplate, String fileName, String tableName){
        this.jdbcTemplate = jdbcTemplate;
        this.fileName = fileName;
        this.tableName = tableName;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        String sql = headMap.get(0).toString();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertRuleSql = "insert into tx.rule(name, sql, table_name, engine_type, create_time, update_time) values (?,?,?,?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertRuleSql, new String[]{"id"});
            ps.setString(1,fileName);
            ps.setString(2, sql);
            ps.setString(3,tableName);
            ps.setString(4,"oracle");
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        String insertZbSql = "insert into tx.zb(rule_id, zb_name, describe, zb_type, zb_input_table, create_time, update_time) values (?,?,?,?,?,?,?)";

        jdbcTemplate.update(insertZbSql, keyHolder.getKey(), fileName, fileName, 2, "result_target_list", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
}