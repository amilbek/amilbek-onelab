package com.example.shop;

import com.example.shop.dto.Car;
import com.example.shop.dto.Request;
import com.example.shop.dto.User;
import com.example.shop.services.CarService;
import com.example.shop.services.RequestService;
import com.example.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Component
public class Application {

    private final UserService userService;
    private final CarService carService;
    private final RequestService requestService;

    @Autowired
    public Application(UserService userService, CarService carService, RequestService requestService) {
        this.userService = userService;
        this.carService = carService;
        this.requestService = requestService;
    }

    public void start() {

    }

    private void printListOfUsers() {
        System.out.println("USERS");
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u.toString());
        }
        System.out.println();
    }

    private void printListOfCars() {
        System.out.println("CARS");
        List<Car> cars = carService.getAllCars();
        for (Car c : cars) {
            System.out.println(c.toString());
        }
        System.out.println();
    }

    private void printListOfRequests() {
        System.out.println("REQUESTS");
        List<Request> requests = requestService.getAllRequests();
        for (Request r : requests) {
            System.out.println(r.toString());
        }
        System.out.println();
    }

    private void printListOfRequestsByUser(User user) {
        System.out.println("REQUESTS BY USER");
        List<Request> requests = requestService.getRequestsByUser(user);
        for (Request r : requests) {
            System.out.println(r.toString());
        }
        System.out.println();
    }

    private void printListOfRequestsByCar(Car car) {
        System.out.println("REQUESTS BY CAR");
        List<Request> requests = requestService.getRequestsByCar(car);
        for (Request r : requests) {
            System.out.println(r.toString());
        }
        System.out.println();
    }
}
