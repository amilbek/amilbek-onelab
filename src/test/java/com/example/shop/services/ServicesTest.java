package com.example.shop.services;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.repository.CarRepository;
import com.example.shop.repository.RequestRepository;
import com.example.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesTest {

    @InjectMocks
    Services sut;

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    RequestRepository requestRepository;

    @Test
    void saveUser() {
        User user = addUser("John", "Wick", "87770806565",
                "13/11/1982");

        when(userRepository.save(user)).thenReturn(user);

        boolean result = sut.saveUser(user);
        assertTrue(result);
    }

    @Test
    void getUserById() {
        User expected = addUser("Tony", "Stark", "87770806555",
                "13/11/1983");

        when(userRepository.findById(1L)).thenReturn(Optional.of(expected));

        User actual = sut.getUserById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void getUserByPhoneNumber() {
        User expected = addUser("Tony", "Stark", "87770806555",
                "13/11/1983");

        when(userRepository.findUserByPhoneNumber(expected.getPhoneNumber())).thenReturn(expected);

        User actual = sut.getUserByPhoneNumber("87770806555");

        assertEquals(expected, actual);
    }

    @Test
    void updateUser() {
        User user = addUser("Tony", "Stark", "87770806555",
                "13/11/1983");

        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = addUser("Anthony", "Crack", "87770806555",
                "13/11/1973");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User newUser = sut.updateUser(updatedUser, 1L);
        assertEquals(updatedUser, newUser);
    }

    @Test
    void deleteUser() {
        User user = addUser("Tony", "Stark", "87770806555",
                "13/11/1983");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = sut.deleteUser(1L);
        assertTrue(result);
    }

    @Test
    void getAllUsers() {
        List<User> expectedUsers = new ArrayList<>();

        User expectedUser = addUser("John", "Wick", "87770806565",
                "13/11/1982");

        expectedUsers.add(expectedUser);

        sut.saveUser(expectedUser);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = sut.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void saveCar() {
        Car car = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        when(carRepository.save(car)).thenReturn(car);

        boolean result = sut.saveCar(car);
        assertTrue(result);
    }

    @Test
    void getCarById() {
        Car expected = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        when(carRepository.findById(1L)).thenReturn(Optional.of(expected));

        Car actual = sut.getCarById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void updateCar() {
        Car car = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        when(carRepository.save(car)).thenReturn(car);

        Car updatedCar = addCar("Mercedes", "E-class", "white",
                2020, 1450.0, false);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Car newCar = sut.updateCar(updatedCar, 1L);
        assertEquals(updatedCar, newCar);
    }

    @Test
    void deleteCar() {
        Car car = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        boolean result = sut.deleteCar(1L);
        assertTrue(result);
    }

    @Test
    void getAllCars() {
        List<Car> expectedCars = new ArrayList<>();

        Car expectedCar = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        expectedCars.add(expectedCar);

        sut.saveCar(expectedCar);

        when(carRepository.findAll()).thenReturn(expectedCars);

        List<Car> actualCars = sut.getAllCars();
        assertEquals(expectedCars, actualCars);
    }

    @Test
    void saveRequest() {
        User user = addUser("John", "Wick", "87770806565",
                "13/11/1982");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Car car = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        Request request = addRequest(user, car, true);

        when(requestRepository.save(request)).thenReturn(request);

        boolean result = sut.saveRequest(request);
        assertTrue(result);
    }

    @Test
    void getRequestByRequestTime() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = addUser("John", "Wick", "87770806565",
                "13/11/1982");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Car car = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        Request request = addRequest(user, car, true);
        when(requestRepository.save(request)).thenReturn(request);

        expectedRequests.add(request);
        sut.saveRequest(request);

        when(requestRepository.findAllByOrderByRequestTimeDesc()).thenReturn(expectedRequests);

        List<Request> actualRequests = sut.getRequestByRequestTime();

        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getRequestsByUser() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = addUser("John", "Wick", "87770806565",
                "13/11/1982");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Car car = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);

        Request request = addRequest(user, car, true);
        when(requestRepository.save(request)).thenReturn(request);

        expectedRequests.add(request);
        sut.saveRequest(request);

        when(requestRepository.findAllByUser(user)).thenReturn(expectedRequests);

        List<Request> actualRequests = sut.getRequestsByUser(user);

        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getRequestsByCar() {
        List<Request> expectedRequests = new ArrayList<>();

        User user = addUser("John", "Wick", "87770806565",
                "13/11/1982");
        user.setId(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        Car car = addCar("Toyota", "Camry", "black",
                2022, 1500.0, true);

        Request request = addRequest(user, car, false);
        when(requestRepository.save(request)).thenReturn(request);

        expectedRequests.add(request);
        sut.saveRequest(request);

        when(requestRepository.findAllByCar(car)).thenReturn(expectedRequests);

        List<Request> actualRequests = sut.getRequestsByCar(car);

        assertEquals(expectedRequests, actualRequests);
    }

    private User addUser(String firstName, String lastName, String phoneNumber, String birthDate) {
        User user = new User();
        try {
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

    private Car addCar(String brand, String model, String color, Integer productionYear, Double price,
                       Boolean isAvailable) {
        Car car = new Car();

        car.setBrand(brand);
        car.setModel(model);
        car.setColor(color);
        car.setProductionYear(productionYear);
        car.setPrice(price);
        car.setIsAvailable(isAvailable);

        return car;
    }

    private Request addRequest(User user, Car car, Boolean isAccepted) {
        Request request = new Request();

        request.setUser(user);
        request.setCar(car);
        request.setRequestTime(new Date(System.currentTimeMillis()));
        request.setIsAccepted(isAccepted);

        return request;
    }
}