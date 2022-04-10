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
import java.util.Date;

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
    }

    @Test
    void getAllRequests() {
    }

    @Test
    void getRequestsByUser() {
    }

    @Test
    void getRequestsByCar() {
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