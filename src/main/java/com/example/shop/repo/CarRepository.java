package com.example.shop.repo;

import com.example.shop.dto.Car;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository {

    Car saveCar(Car car);

    List<Car> getAllCars();
}
