package com.example.shop.services;

import com.example.shop.entity.Car;
import com.example.shop.repository.impl.ICarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    ICarRepository carRepository;

    @Test
    void saveCar() {
        Car car = addCar(1L, "Mercedes", "S-class", "black",
                2020, 1500, true);

        when(carRepository.saveCar(car)).thenReturn(car);

        boolean result = carService.saveCar(car);
        assertTrue(result);
    }

    @Test
    void getAllCars() {
        List<Car> expectedCars = new ArrayList<>();

        Car expectedCar1 = addCar(1L, "Mercedes", "S-class", "black",
                2020, 1500, true);

        Car expectedCar2 = addCar(2L, "Toyota", "Camry", "white",
                2010, 1100, false);

        expectedCars.add(expectedCar1);
        expectedCars.add(expectedCar2);

        carService.saveCar(expectedCar1);
        carService.saveCar(expectedCar2);

        when(carRepository.getAllCars()).thenReturn(expectedCars);

        List<Car> actualCars = carService.getAllCars();
        assertEquals(expectedCars, actualCars);
    }

    private Car addCar(Long id, String brand, String model, String color, int productionYear, double price,
                       boolean isAvailable) {
        Car car = new Car();

        car.setId(id);
        car.setBrand(brand);
        car.setModel(model);
        car.setColor(color);
        car.setProductionYear(productionYear);
        car.setPrice(price);
        car.setAvailable(isAvailable);

        return car;
    }
}