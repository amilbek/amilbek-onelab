package com.example.shop.services;

import com.example.shop.entity.Car;
import com.example.shop.repository.impl.ICarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final ICarRepository carRepository;

    @Autowired
    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public boolean saveCar(Car carIn) {
        Car car = new Car();
        car.setId(carIn.getId());
        car.setBrand(carIn.getBrand());
        car.setModel(carIn.getModel());
        car.setColor(carIn.getColor());
        car.setProductionYear(carIn.getProductionYear());
        car.setPrice(carIn.getPrice());
        car.setAvailable(carIn.isAvailable());

        carRepository.saveCar(car);

        return true;
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }
}
