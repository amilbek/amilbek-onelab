package com.example.shop.services;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.repository.impl.ICarRepository;
import com.example.shop.repository.impl.IRequestRepository;
import com.example.shop.repository.impl.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @InjectMocks
    RequestService requestService;

    @Mock
    IRequestRepository requestRepository;

    @Mock
    IUserRepository userRepository;

    @Mock
    ICarRepository carRepository;

    @Test
    void saveRequest() {
        User user = addUser(1L, "John", "Wick", "87770806565",
            "13/11/1982");

        Car car = addCar(1L, "Mercedes", "S-class", "black",
                2020, 1500, true);

        Request request = addRequest(1L, 1L, 1L);

        when(requestRepository.saveRequest(request)).thenReturn(request);

        boolean result = requestService.saveRequest(request);
        assertTrue(result);
    }

    @Test
    void getAllRequests() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = addUser(1L, "Tony", "Stark", "87770706565",
                "13/11/1972");

        Car car = addCar(1L, "Mercedes", "S-class", "black",
                2020, 1500, true);

        Request request = addRequest(1L, user.getId(), car.getId());

        expectedRequests.add(request);
        requestService.saveRequest(request);

        when(requestRepository.getAllRequests()).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.getAllRequests();
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getRequestsByUser() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = addUser(1L, "John", "Wick", "87770806565",
                "13/11/1982");

        Car car = addCar(1L, "Toyota", "Camry", "white",
                2010, 1400, false);

        Request request = addRequest(2L, user.getId(), car.getId());

        expectedRequests.add(request);
        requestService.saveRequest(request);

        when(requestRepository.getRequestsByUser(user)).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.getRequestsByUser(user);
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getRequestsByCar() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = addUser(2L, "John", "Wick", "87770806565",
                "13/11/1982");

        Car car = addCar(2L, "Mercedes", "S-class", "black",
                2020, 1500, true);

        Request request = addRequest(3L, user.getId(), car.getId());

        expectedRequests.add(request);
        requestService.saveRequest(request);

        when(requestRepository.getRequestsByCar(car)).thenReturn(expectedRequests);

        List<Request> actualRequests = requestService.getRequestsByCar(car);
        assertEquals(expectedRequests, actualRequests);
    }

    private User addUser(Long id, String firstName, String lastName, String phoneNumber, String birthDate) {
        User user = new User();
        try {
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
            user.setBirthDate(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
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

    private Request addRequest(Long id, Long userId, Long carId) {
        Request request = new Request();

        request.setId(id);
        request.setUserId(userId);
        request.setCarId(carId);
        request.setRequestTime(new Date(System.currentTimeMillis()));

        return request;
    }
}