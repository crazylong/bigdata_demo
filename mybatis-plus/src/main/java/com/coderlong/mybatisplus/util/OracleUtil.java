package com.coderlong.mybatisplus.util;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OracleUtil {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<String> getColumnList(String schemaName, String tableName) throws SQLException {

        List<String> list = new ArrayList<>();

        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            DatabaseMetaData dbmd = conn.getMetaData();
            if(StringUtils.isEmpty(schemaName)){
                schemaName = dbmd.getUserName();
            }
            rs = dbmd.getColumns(null, schemaName, tableName, null);

            while (rs.next()) {
                //. Column column = new Column();
                //column.setAutoincrement(rs.getString("IS_AUTOINCREMENT"));
                //column.setColumnName(rs.getString("COLUMN_NAME"));
//                column.setColumnSize(rs.getInt("COLUMN_SIZE"));
//                column.setDataType(rs.getInt("DATA_TYPE"));
//                column.setNullable(rs.getInt("NULLABLE"));
//                column.setRemarks(rs.getString("REMARKS"));
//                column.setTableCat(rs.getString("TABLE_CAT"));
//                column.setTableSchem(rs.getString("TABLE_SCHEM"));
//                column.setTypeName(rs.getString("TYPE_NAME"));
                list.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
