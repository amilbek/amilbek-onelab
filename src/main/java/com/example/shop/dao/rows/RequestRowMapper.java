package com.example.shop.dao.rows;

import com.example.shop.entity.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestRowMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();

        request.setId(rs.getLong("id"));
        request.setUserId(rs.getLong("user_id"));
        request.setCarId(rs.getLong("car_id"));
        request.setRequestTime(rs.getDate("request_time"));

        return request;
    }
}
