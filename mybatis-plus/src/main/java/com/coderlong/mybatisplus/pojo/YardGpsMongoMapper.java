package com.coderlong.mybatisplus.pojo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YardGpsMongoMapper implements RowMapper<YardGpsMongo> {

        @Override
        public YardGpsMongo mapRow(ResultSet rs, int rowNum) throws SQLException {
            YardGpsMongo yardGpsMongo = new YardGpsMongo(
                    rs.getLong("GPS_ID"),
                    rs.getString("GPS_YARD_CODE"),
                    rs.getString("GPS_MAPCLASS"),
                    rs.getString("GPS_MAPID"),
                    rs.getString("GPS_MAPNM"),
                    rs.getString("GPS_GRAPH"),
                    rs.getString("GPS_COORDINATES"),
                    rs.getDate("GPS_INSERT_TIME"),
                    rs.getString("GPS_TERMINAL_CODE"));
            return yardGpsMongo;
        }
    }