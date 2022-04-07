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

        User user1 = addUser("John", "Wick", "87776669955", "1972-11-30");
        if (userService.createUser(user1)) {
            System.out.println("Created User\n" + user1.toString() + "\n");
        }

        User user2 = addUser("Tony", "Stark", "87776669955", "1972-01-30");
        if (userService.createUser(user2)) {
            System.out.println("Created User\n" + user2.toString() + "\n");
        }

        User user3 = addUser("John", "Snow", "87776669945", "1974-02-28");
        if (userService.createUser(user3)) {
            System.out.println("Created User\n" + user3.toString() + "\n");
        }

        User user4 = userService.getUserByPhoneNumber("87776669945");
        if (user4 != null) {
            System.out.println("Getting User by Phone Number: " + user4.toString() + "\n");
        }

        User user5 = userService.getUserByPhoneNumber("87776664945");
        if (user5 != null) {
            System.out.println("Getting User by Phone Number: " + user5.toString() + "\n");
        }

        Car car1 = addCar(1L, "Mercedes" ,"S-class", "black", "2020" , 1500, true);
        if (carService.saveCar(car1)) {
            System.out.println("Created Car\n" + car1.toString() + "\n");
        }

        Car car2 = addCar(2L, "Toyota" ,"Camry", "white", "2015" , 1490, false);
        if (carService.saveCar(car2)) {
            System.out.println("Created Car\n" + car2.toString() + "\n");
        }

        Request request1 = addRequest(1L, user1, car1);
        if (request1 != null && requestService.saveRequest(request1)) {
            System.out.println("Created Request\n" + request1.toString() + "\n");
        }

        Request request2 = addRequest(2L, user2, car1);
        if (request2 != null &&requestService.saveRequest(request2)) {
            System.out.println("Created Request\n" + request2.toString() + "\n");
        }

        Request request3 = addRequest(3L, user1, car2);
        if (request3 != null &&requestService.saveRequest(request3)) {
            System.out.println("Created Request\n" + request3.toString() + "\n");
        }

        printListOfUsers();
        printListOfCars();
        printListOfRequests();
        printListOfRequestsByUser(user2);
        printListOfRequestsByCar(car1);
    }

    private User addUser(String firstName, String lastName, String phoneNumber, String birthDate) {
        return User.userBuilder()
                .fName(firstName)
                .lName(lastName)
                .phoneNumber(phoneNumber)
                .date(LocalDate.parse(birthDate))
                .build();
    }

    private Car addCar(Long carNumber, String brand, String model, String color,
                       String productionYear, double price, boolean isAvailable) {
        return Car.carBuilder()
                .carNumber(carNumber)
                .brand(brand)
                .model(model)
                .color(color)
                .year(Year.parse(productionYear))
                .price(price)
                .availability(isAvailable)
                .build();
    }

    private Request addRequest(Long id, User user, Car car) {
        List<User> users = userService.getAllUsers();
        if (!users.contains(user)) {
            return null;
        }
        return Request.requestBuilder()
                .id(id)
                .user(user)
                .car(car)
                .time()
                .build();
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
