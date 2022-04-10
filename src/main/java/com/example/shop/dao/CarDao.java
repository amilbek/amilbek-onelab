package com.example.shop.dao;

import com.example.shop.entity.Car;

import java.util.List;

public interface CarDao {

    Car saveCar(Car car);

    List<Car> getAllCars();
}
