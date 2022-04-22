package com.example.shop.dto.transition;

import com.example.shop.dto.CarDTO;
import com.example.shop.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarTransition {

    public CarDTO carToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();

        carDTO.setCarNumber(car.getCarNumber());
        carDTO.setBrand(car.getBrand());
        carDTO.setModel(car.getModel());
        carDTO.setColor(car.getColor());
        carDTO.setPrice(car.getPrice());
        carDTO.setProductionYear(car.getProductionYear());
        carDTO.setIsAvailable(car.getIsAvailable());

        return carDTO;
    }
}
