package com.example.shop.controllers;

import com.example.shop.dto.CarDTO;
import com.example.shop.dto.RequestDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.dto.transition.CarTransition;
import com.example.shop.dto.transition.RequestTransition;
import com.example.shop.dto.transition.UserTransition;
import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final Services services;
    private final UserTransition userTransition;
    private final CarTransition carTransition;
    private final RequestTransition requestTransition;

    @Autowired
    public AdminController(Services services,
                           UserTransition userTransition,
                           CarTransition carTransition,
                           RequestTransition requestTransition) {
        this.services = services;
        this.userTransition = userTransition;
        this.carTransition = carTransition;
        this.requestTransition = requestTransition;
    }

    /**
    JWT in Authorization Header
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = services.getAllUsers();

        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = userTransition.userToUserDTO(user);
            userDTOList.add(userDTO);
        }

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header
     */
    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<Car> cars = services.getAllCars();

        List<CarDTO> carDTOList = new ArrayList<>();

        for (Car car : cars) {
            CarDTO carDTO = carTransition.carToCarDTO(car);
            carDTOList.add(carDTO);
        }

        return new ResponseEntity<>(carDTOList, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header
     */
    @GetMapping("/requests")
    public ResponseEntity<List<RequestDTO>> getAllRequests() {
        List<Request> requests = services.getRequestByRequestTime();

        List<RequestDTO> requestDTOList = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO requestDTO = requestTransition.requestToRequestDTO(request);
            requestDTOList.add(requestDTO);
        }

        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header & username (tony_stark) in Path Variable Params
     */
    @GetMapping("/requests/users/{username}")
    public ResponseEntity<List<RequestDTO>> getRequestByUser(@PathVariable(value = "username")
                                                                         String username) {
        List<Request> requests = services.getRequestsByUser(username);

        List<RequestDTO> requestDTOList = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO requestDTO = requestTransition.requestToRequestDTO(request);
            requestDTOList.add(requestDTO);
        }

        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header & car number (080QQQ08) in Path Variable Params
     */
    @GetMapping("/requests/cars/{carNumber}")
    public ResponseEntity<List<RequestDTO>> getRequestByCar(@PathVariable(value = "carNumber")
                                                                     String carNumber) {
        List<Request> requests = services.getRequestsByCar(carNumber);

        List<RequestDTO> requestDTOList = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO requestDTO = requestTransition.requestToRequestDTO(request);
            requestDTOList.add(requestDTO);
        }

        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }
}
