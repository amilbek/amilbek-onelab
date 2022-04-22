package com.example.shop.controllers;

import com.example.shop.dto.RequestDTO;
import com.example.shop.dto.transition.RequestTransition;
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

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    @InjectMocks
    RequestController sut;

    @Mock
    Services services;

    @Mock
    RequestTransition requestTransition;

    @Mock
    Principal principal;

    @Test
    void createRequest() {
        User user = addUser("firstname", "lastname",
                "87770806565", "1982-11-13", "username");

        Car car = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);

        Request request = new Request();
        request.setUser(user);
        request.setCar(car);
        request.setIsAccepted(false);
        request.setRequestTime(LocalDateTime.now());

        RequestDTO requestDTO = requestToRequestDTO(request);

        ResponseEntity<RequestDTO> expected = new ResponseEntity<>(requestDTO, HttpStatus.OK);

        when(services.saveRequest(requestDTO, principal)).thenReturn(true);

        ResponseEntity<RequestDTO> actual = sut.createRequest(requestDTO, principal);

        assertEquals(expected, actual);
    }

    @Test
    void getAllRequestsByCurrentUser() {
        List<RequestDTO> requestDTOList = new ArrayList<>();
        List<Request> requests = new ArrayList<>();

        User user = addUser("firstName", "lastName",
                "87770801565", "1982-11-11", "username1");

        Car car = addCar("carNumber1", "brand1", "model1",
                "color1",  2010,  1100.0, false);

        Request request = new Request();
        request.setUser(user);
        request.setCar(car);
        request.setIsAccepted(false);
        request.setRequestTime(LocalDateTime.now());
        requests.add(request);

        RequestDTO requestDTO = requestToRequestDTO(request);
        requestDTOList.add(requestDTO);

        when(requestTransition.requestToRequestDTO(request)).thenReturn(requestDTO);
        when(services.getRequestsByCurrentUser(principal)).thenReturn(requests);

        ResponseEntity<List<RequestDTO>> expected = new ResponseEntity<>(requestDTOList, HttpStatus.OK);
        ResponseEntity<List<RequestDTO>> actual = sut.getAllRequestsByCurrentUser(principal);

        assertEquals(expected, actual);
    }

    private User addUser(String firstName, String lastName, String phoneNumber, String birthDate,
                               String username) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        LocalDate date = LocalDate.parse(birthDate);
        user.setBirthDate(date);
        user.setUsername(username);
        return user;
    }

    private Car addCar(String carNumber, String brand, String model,
                          String color, Integer productionYear, Double price,
                          Boolean isAvailable) {
        Car car = new Car();

        car.setCarNumber(carNumber);
        car.setBrand(brand);
        car.setModel(model);
        car.setColor(color);
        car.setProductionYear(productionYear);
        car.setPrice(price);
        car.setIsAvailable(isAvailable);

        return car;
    }

    private RequestDTO requestToRequestDTO(Request request) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUsername(request.getUser().getUsername());
        requestDTO.setCarNumber(request.getCar().getCarNumber());
        requestDTO.setIsAccepted(request.getIsAccepted());
        requestDTO.setRequestTime(request.getRequestTime());
        return requestDTO;
    }
}