package com.example.shop.repository.impl;

import com.example.shop.dao.impl.ICarDao;
import com.example.shop.entity.Car;
import com.example.shop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ICarRepository implements CarRepository {

    private final ICarDao carDao;

    @Autowired
    public ICarRepository(ICarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public Car saveCar(Car car) {
        return carDao.saveCar(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }
}
