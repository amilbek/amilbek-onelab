package com.example.shop.repo.impl;

import com.example.shop.dto.Car;
import com.example.shop.repo.CarRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ICarRepository implements CarRepository {

    private final Map<Long, Car> cars = new HashMap<>();

    @Override
    public Car saveCar(Car car) {
        cars.put(car.getCarNumber(), car);
        return cars.get(car.getCarNumber());
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }
}
