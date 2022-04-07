package com.example.shop.services;

import com.example.shop.dto.Car;
import com.example.shop.dto.Request;
import com.example.shop.dto.User;
import com.example.shop.repository.impl.IRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @InjectMocks
    RequestService requestService;

    @Mock
    IRequestRepository requestRepository;

    @Test
    void saveRequest() {
        User user = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();

        Car car = Car.carBuilder()
                .carNumber(1L)
                .brand("Mercedes")
                .model("S-class")
                .color("black")
                .year(Year.parse("2020"))
                .price(1500)
                .availability(true)
                .build();

        Request request = Request.requestBuilder()
                .id(1L)
                .user(user)
                .car(car)
                .time()
                .build();

        when(requestRepository.saveRequest(request)).thenReturn(request);

        boolean result = requestService.saveRequest(request);
        assertTrue(result);
    }

    @Test
    void getAllRequests() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();

        Car car = Car.carBuilder()
                .carNumber(1L)
                .brand("Mercedes")
                .model("S-class")
                .color("black")
                .year(Year.parse("2020"))
                .price(1500)
                .availability(true)
                .build();

        Request request = Request.requestBuilder()
                .id(1L)
                .user(user)
                .car(car)
                .time()
                .build();

        expectedRequests.add(request);
        requestService.saveRequest(request);

        when(requestRepository.getAllRequests()).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.getAllRequests();
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getRequestsByUser() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();

        Car car = Car.carBuilder()
                .carNumber(1L)
                .brand("Mercedes")
                .model("S-class")
                .color("black")
                .year(Year.parse("2020"))
                .price(1500)
                .availability(true)
                .build();

        Request request = Request.requestBuilder()
                .id(1L)
                .user(user)
                .car(car)
                .time()
                .build();

        expectedRequests.add(request);
        requestService.saveRequest(request);

        when(requestRepository.getRequestsByUser(user)).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.getRequestsByUser(user);
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getRequestsByCar() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();

        Car car = Car.carBuilder()
                .carNumber(1L)
                .brand("Mercedes")
                .model("S-class")
                .color("black")
                .year(Year.parse("2020"))
                .price(1500)
                .availability(true)
                .build();

        Request request = Request.requestBuilder()
                .id(1L)
                .user(user)
                .car(car)
                .time()
                .build();

        expectedRequests.add(request);
        requestService.saveRequest(request);

        when(requestRepository.getRequestsByCar(car)).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.getRequestsByCar(car);
        assertEquals(expectedRequests, actualRequests);
    }
}