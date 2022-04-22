package com.example.shop;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.repository.CarRepository;
import com.example.shop.repository.RequestRepository;
import com.example.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Data {

    @Bean
    public CommandLineRunner addUsersToDB(UserRepository userRepository,
                                          CarRepository carRepository,
                                          RequestRepository requestRepository) {
        return (String[] args) -> {
            User user1 = addUser("Tony", "Stark", LocalDate.parse("1982-11-13"),
                    "87775553366", "tony_stark",
                    //password: tony_stark
                    "$2a$10$nt0TmxsLc31v9Tscn7cOgOjcWCXm1zx45JWZbx8ShJduAlND/cHiW");
            User user2 = addUser("Steve", "Rodgers", LocalDate.parse("1962-10-10"),
                    "87775553663", "steve_rodgers",
                    //password: steve_rodgers
                    "$2a$10$mUDh4PodYN7BY.W6ol7mtuaRZXgvtxhu8dtShyLCKc7LU71x0aO96");
            userRepository.save(user1);
            userRepository.save(user2);

            Car car1 = addCar("888QQQ08", "Mercedes", "S-class", "Black",
                    2020, 1500.0, true);
            Car car2 = addCar("080OOO08", "Lexus", "570", "White",
                    2019, 1450.0, false);
            carRepository.save(car1);
            carRepository.save(car2);

            Request request1 = addRequest(user1, car1);
            Request request2 = addRequest(user2, car2);
            requestRepository.save(request1);
            requestRepository.save(request2);
        };
    }

    private User addUser(String firstName, String lastName, LocalDate birthDate, String phoneNumber,
                         String username, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setPassword(password);
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

    private Request addRequest(User user, Car car) {
        Request request = new Request();
        request.setUser(user);
        request.setCar(car);
        request.setRequestTime(LocalDateTime.now());
        request.setIsAccepted(false);
        return request;
    }
}
