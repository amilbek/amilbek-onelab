package com.example.shop.controllers;

import com.example.shop.constants.Constants;
import com.example.shop.dto.CarDTO;
import com.example.shop.dto.transition.CarTransition;
import com.example.shop.entity.Car;
import com.example.shop.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final Services services;
    private final CarTransition carTransition;

    @Autowired
    public CarController(Services services,
                         CarTransition carTransition) {
        this.services = services;
        this.carTransition = carTransition;
    }

    /**
    JWT in Authorization Header
        "carNumber": "080QQQ08",
        "brand": "Toyota",
        "model": "Camry",
        "color": "black",
        "productionYear": "2020",
        "price": "1500",
        "isAvailable": true
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarDTO carDTO) {
        boolean result = services.saveCar(carDTO);

        if (!result) {
            return new ResponseEntity<>(Constants.ADDING_CAR_FAILED, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Constants.ADDING_CAR_SUCCEED, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header & ID (1) in Path Variables Params
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable(value = "id") String id) {
        Car car = services.getCarById(Long.parseLong(id));
        CarDTO carDTO = carTransition.carToCarDTO(car);

        return new ResponseEntity<>(carDTO, HttpStatus.OK);
    }

    /**
       JWT in Authorization Header & car number (080QQQ08) in Path Variables Params
    */
    @GetMapping("/car/{carNumber}")
    public ResponseEntity<CarDTO> getCarByCaNumber(@PathVariable(value = "carNumber") String carNumber) {
        Car car = services.getCarByCarNumber(carNumber);
        CarDTO carDTO = carTransition.carToCarDTO(car);

        return new ResponseEntity<>(carDTO, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header & ID (1)
        "carNumber": "080QQQ08",
        "brand": "Toyota",
        "model": "Corolla",
        "color": "black",
        "productionYear": "2010",
        "price": "1500",
        "isAvailable": true
    */
    @PutMapping("/update/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable(value = "id") String id,
                                            @Valid @RequestBody CarDTO carDTO) {
        Car car = services.updateCar(carDTO, Long.parseLong(id));
        CarDTO updatedCar = carTransition.carToCarDTO(car);

        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header & ID (1)
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable(value = "id") String id) {
        boolean result = services.deleteCar(Long.parseLong(id));
        if (!result) {
            return new ResponseEntity<>(Constants.DELETING_CAR_FAILED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Constants.DELETING_CAR_SUCCEED, HttpStatus.OK);
    }
}
