package com.example.shop.dao.impl;

import com.example.shop.dao.RequestDao;
import com.example.shop.dao.rows.RequestRowMapper;
import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IRequestDao implements RequestDao, InitializingBean {

    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_REQUEST_QUERY = "INSERT INTO " +
            "requests(id, user_id, car_id, request_time) VALUES(?, ?, ?, ?)";
    private static final String GET_ALL_REQUESTS_QUERY = "SELECT * FROM requests";
    private static final String GET_ALL_REQUESTS_BY_USER_QUERY = "SELECT * FROM requests WHERE user_id = ?";
    private static final String GET_ALL_REQUESTS_BY_CAR_QUERY = "SELECT * FROM requests WHERE car_id = ?";

    @Autowired
    public IRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Request saveRequest(Request request) {
        jdbcTemplate.update(SAVE_REQUEST_QUERY, request.getId(), request.getUserId(), request.getCarId(),
                request.getRequestTime());
        return request;
    }

    @Override
    public List<Request> getAllRequests() {
        return jdbcTemplate.query(GET_ALL_REQUESTS_QUERY, new RequestRowMapper());
    }

    @Override
    public List<Request> getRequestsByUser(User user) {
        return jdbcTemplate.query(GET_ALL_REQUESTS_BY_USER_QUERY, new RequestRowMapper(), user.getId());
    }

    @Override
    public List<Request> getRequestsByCar(Car car) {
        return jdbcTemplate.query(GET_ALL_REQUESTS_BY_CAR_QUERY, new RequestRowMapper(), car.getId());
    }

    @Override
    public void afterPropertiesSet() {
        if (jdbcTemplate == null) {
            throw new NullPointerException("JDBC Template is null");
        }
    }
}
