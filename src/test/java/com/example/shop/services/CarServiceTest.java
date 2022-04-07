package com.example.shop.services;

import com.example.shop.dto.Car;
import com.example.shop.repository.impl.ICarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    ICarRepository carRepository;

    @Test
    void saveCar() {
        Car car = Car.carBuilder()
                .carNumber(1L)
                .brand("Mercedes")
                .model("S-class")
                .color("black")
                .year(Year.parse("2020"))
                .price(1500)
                .availability(true)
                .build();

        when(carRepository.saveCar(car)).thenReturn(car);

        boolean result = carService.saveCar(car);
        assertTrue(result);
    }

    @Test
    void getAllCars() {
        List<Car> expectedCars = new ArrayList<>();

        Car car1 = Car.carBuilder()
                .carNumber(1L)
                .brand("Mercedes")
                .model("S-class")
                .color("black")
                .year(Year.parse("2020"))
                .price(1500)
                .availability(true)
                .build();

        expectedCars.add(car1);
        carService.saveCar(car1);


        Car car2 = Car.carBuilder()
                .carNumber(1L)
                .brand("Toyota")
                .model("Camry")
                .color("white")
                .year(Year.parse("2010"))
                .price(150)
                .availability(true)
                .build();

        expectedCars.add(car2);
        carService.saveCar(car2);

        when(carRepository.getAllCars()).thenReturn(expectedCars);

        List<Car> actualCars = carService.getAllCars();
        assertEquals(expectedCars, actualCars);
    }
}