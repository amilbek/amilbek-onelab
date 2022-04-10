package com.example.shop.dao.impl;

import com.example.shop.dao.CarDao;
import com.example.shop.dao.rows.CarRowMapper;
import com.example.shop.entity.Car;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ICarDao implements CarDao, InitializingBean {

    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_CAR_QUERY = "INSERT INTO " +
            "cars(id, brand, model, color, production_year, price, is_available) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_CARS_QUERY = "SELECT * FROM cars";

    @Autowired
    public ICarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car saveCar(Car car) {
        jdbcTemplate.update(SAVE_CAR_QUERY, car.getId(), car.getBrand(), car.getModel(), car.getColor(),
                car.getProductionYear(), car.getPrice(), car.isAvailable());
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        return jdbcTemplate.query(GET_ALL_CARS_QUERY, new CarRowMapper());
    }

    @Override
    public void afterPropertiesSet() {
        if (jdbcTemplate == null) {
            throw new NullPointerException("JDBC Template is null");
        }
    }
}
