package com.example.shop.dao.rows;

import com.example.shop.entity.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();

        car.setId(rs.getLong("id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setColor(rs.getString("color"));
        car.setProductionYear(rs.getInt("production_year"));
        car.setPrice(rs.getDouble("price"));
        car.setAvailable(rs.getBoolean("is_available"));

        return car;
    }
}
