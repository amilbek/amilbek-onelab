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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    AdminController sut;

    @Mock
    Services services;

    @Mock
    UserTransition userTransition;

    @Mock
    CarTransition carTransition;

    @Mock
    RequestTransition requestTransition;

    @Test
    void getAllUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = new ArrayList<>();

        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        userDTOList.add(userDTO);
        userList.add(user);

        when(services.getAllUsers()).thenReturn(userList);
        when(userTransition.userToUserDTO(user)).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> expected = new ResponseEntity<>(userDTOList, HttpStatus.OK);
        ResponseEntity<List<UserDTO>> actual = sut.getAllUsers();

        assertEquals(expected, actual);
    }

    @Test
    void getAllCars() {
        List<CarDTO> carDTOList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        carDTOList.add(carDTO);
        carList.add(car);

        when(services.getAllCars()).thenReturn(carList);
        when(carTransition.carToCarDTO(car)).thenReturn(carDTO);

        ResponseEntity<List<CarDTO>> expected = new ResponseEntity<>(carDTOList, HttpStatus.OK);
        ResponseEntity<List<CarDTO>> actual = sut.getAllCars();

        assertEquals(expected, actual);
    }

    @Test
    void getAllRequests() {
        List<RequestDTO> requestDTOList = new ArrayList<>();
        List<Request> requestList = new ArrayList<>();

        UserDTO userDTO = addUserDTO("firstName", "lastName",
                "87770806515", "1981-11-13", "username1");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber1", "brand1", "model1",
                "color1",  2010,  1400.0, true);
        Car car = carDtoToCar(carDTO);

        Request request = addRequest(user, car);
        requestList.add(request);

        RequestDTO requestDTO = addRequestDTO(user.getUsername(), car.getCarNumber(),
                request.getRequestTime());
        requestDTOList.add(requestDTO);

        when(requestTransition.requestToRequestDTO(request)).thenReturn(requestDTO);
        when(services.getRequestByRequestTime()).thenReturn(requestList);

        ResponseEntity<List<RequestDTO>> expected = new ResponseEntity<>(requestDTOList, HttpStatus.OK);
        ResponseEntity<List<RequestDTO>> actual = sut.getAllRequests();

        assertEquals(expected, actual);
    }

    @Test
    void getRequestByUser() {
        List<RequestDTO> requestDTOList = new ArrayList<>();
        List<Request> requestList = new ArrayList<>();

        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        Request request = addRequest(user, car);
        requestList.add(request);

        RequestDTO requestDTO = addRequestDTO(user.getUsername(), car.getCarNumber(),
                request.getRequestTime());
        requestDTOList.add(requestDTO);

        when(requestTransition.requestToRequestDTO(request)).thenReturn(requestDTO);
        when(services.getRequestsByUser("username")).thenReturn(requestList);

        ResponseEntity<List<RequestDTO>> expected = new ResponseEntity<>(requestDTOList, HttpStatus.OK);
        ResponseEntity<List<RequestDTO>> actual = sut.getRequestByUser("username");

        assertEquals(expected, actual);
    }

    @Test
    void getRequestByCar() {
        List<RequestDTO> requestDTOList = new ArrayList<>();
        List<Request> requestList = new ArrayList<>();

        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, false);
        Car car = carDtoToCar(carDTO);

        Request request = addRequest(user, car);
        requestList.add(request);

        RequestDTO requestDTO = addRequestDTO(user.getUsername(), car.getCarNumber(),
                request.getRequestTime());
        requestDTOList.add(requestDTO);

        when(requestTransition.requestToRequestDTO(request)).thenReturn(requestDTO);
        when(services.getRequestsByCar("carNumber")).thenReturn(requestList);

        ResponseEntity<List<RequestDTO>> expected = new ResponseEntity<>(requestDTOList, HttpStatus.OK);
        ResponseEntity<List<RequestDTO>> actual = sut.getRequestByCar("carNumber");

        assertEquals(expected, actual);
    }

    private UserDTO addUserDTO(String firstName, String lastName, String phoneNumber, String birthDate,
                               String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPhoneNumber(phoneNumber);
        LocalDate date = LocalDate.parse(birthDate);
        userDTO.setBirthDate(date);
        userDTO.setUsername(username);
        return userDTO;
    }

    private User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBirthDate(userDTO.getBirthDate());
        user.setUsername(userDTO.getUsername());
        return user;
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

    private Request addRequest(User user, Car car) {
        Request request = new Request();
        request.setUser(user);
        request.setCar(car);
        request.setRequestTime(LocalDateTime.now());
        request.setIsAccepted(false);
        return request;
    }

    private RequestDTO addRequestDTO(String username, String carNumber, LocalDateTime localDateTime) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUsername(username);
        requestDTO.setCarNumber(carNumber);
        requestDTO.setRequestTime(localDateTime);
        requestDTO.setIsAccepted(false);
        return requestDTO;
    }
}