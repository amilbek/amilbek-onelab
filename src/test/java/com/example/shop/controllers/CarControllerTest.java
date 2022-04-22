package com.example.shop.controllers;

import com.example.shop.constants.Constants;
import com.example.shop.dto.CarDTO;
import com.example.shop.dto.transition.CarTransition;
import com.example.shop.entity.Car;
import com.example.shop.services.Services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @InjectMocks
    CarController sut;

    @Mock
    Services services;

    @Mock
    CarTransition carTransition;

    @Test
    void createCar() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);

        when(services.saveCar(carDTO)).thenReturn(true);

        ResponseEntity<Object> expected = new ResponseEntity<>(Constants.ADDING_CAR_SUCCEED, HttpStatus.OK);

        ResponseEntity<Object> actual = sut.createCar(carDTO);
        assertEquals(expected, actual);
    }

    @Test
    void getCarById() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        when(carTransition.carToCarDTO(car)).thenReturn(carDTO);
        when(services.getCarById(1L)).thenReturn(car);

        ResponseEntity<CarDTO> expected = new ResponseEntity<>(carDTO, HttpStatus.OK);
        ResponseEntity<CarDTO> actual = sut.getCarById(String.valueOf(1L));

        assertEquals(expected, actual);
    }

    @Test
    void getCarByCarNumber() {
        CarDTO carDTO = addCar("carNumber1", "brand1", "model1",
                "color1",  2010,  2500.0, false);
        Car car = carDtoToCar(carDTO);

        when(carTransition.carToCarDTO(car)).thenReturn(carDTO);
        when(services.getCarByCarNumber("carNumber1")).thenReturn(car);

        ResponseEntity<CarDTO> expected = new ResponseEntity<>(carDTO, HttpStatus.OK);
        ResponseEntity<CarDTO> actual = sut.getCarByCaNumber("carNumber1");

        assertEquals(expected, actual);
    }

    @Test
    void updateCar() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        when(services.updateCar(carDTO, 1L)).thenReturn(car);
        when(carTransition.carToCarDTO(car)).thenReturn(carDTO);

        ResponseEntity<Object> expected = new ResponseEntity<>(carDTO, HttpStatus.OK);

        ResponseEntity<CarDTO> actual = sut.updateCar(String.valueOf(1L), carDTO);
        assertEquals(expected, actual);
    }

    @Test
    void deleteCar() {
        when(services.deleteCar(1L)).thenReturn(true);

        ResponseEntity<Object> expected = new ResponseEntity<>(Constants.DELETING_CAR_SUCCEED, HttpStatus.OK);
        ResponseEntity<Object> actual = sut.deleteCar(String.valueOf(1L));

        assertEquals(expected, actual);
    }

    private CarDTO addCar(String carNumber, String brand, String model, String color, Integer productionYear,
                          Double price,
                          Boolean isAvailable) {
        CarDTO carDTO = new CarDTO();

        carDTO.setCarNumber(carNumber);
        carDTO.setBrand(brand);
        carDTO.setModel(model);
        carDTO.setColor(color);
        carDTO.setProductionYear(productionYear);
        carDTO.setPrice(price);
        carDTO.setIsAvailable(isAvailable);

        return carDTO;
    }

    private Car carDtoToCar(CarDTO carDTO) {
        Car car = new Car();

        car.setCarNumber(carDTO.getCarNumber());
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setColor(carDTO.getColor());
        car.setPrice(carDTO.getPrice());
        car.setProductionYear(carDTO.getProductionYear());
        car.setIsAvailable(carDTO.getIsAvailable());

        return car;
    }
}