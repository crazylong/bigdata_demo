package com.coderlong.mybatisplus.mapper;

import com.coderlong.mybatisplus.pojo.YardGpsMongo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YardGpsMongoMapper implements RowMapper<YardGpsMongo> {

        @Override
        public YardGpsMongo mapRow(ResultSet rs, int rowNum) throws SQLException {
            YardGpsMongo yardGpsMongo = new YardGpsMongo(
                    rs.getLong("ID"),
                    rs.getString("YARD_CODE"),
                    rs.getString("MAPCLASS"),
                    rs.getString("MAPID"),
                    rs.getString("MAPNM"),
                    rs.getString("GRAPH"),
                    rs.getString("COORDINATES"),
                    rs.getDate("INSERT_TIME"),
                    rs.getString("TERMINAL_CODE"),
                    rs.getLong("STATUS_ID")
            );
            return yardGpsMongo;
        }
    }