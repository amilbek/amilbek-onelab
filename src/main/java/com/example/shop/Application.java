package com.example.shop;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.services.CarService;
import com.example.shop.services.RequestService;
import com.example.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        User user1 = addUser(1L, "John", "Wick", "87770806565",
                "13/11/1982");
        if (userService.createUser(user1)) {
            System.out.println("Created user: " + user1.toString() + "\n");
        }

        User user2 = addUser(2L, "John", "Snow", "87770806565",
                "13/11/1972");
        if (userService.createUser(user2)) {
            System.out.println("Created user: " + user2.toString() + "\n");
        }

        User user3 = addUser(3L, "Sam", "Winchester", "87770806566",
                "13/11/1992");
        if (userService.createUser(user3)) {
            System.out.println("Created user: " + user3.toString() + "\n");
        }

        User user4 = userService.getUserByPhoneNumber("87770806565");
        if (user4 != null) {
            System.out.println("Getting User by Phone Number: " + user4.toString() + "\n");
        }

        User user5 = userService.getUserByPhoneNumber("87775553366");
        if (user5 != null) {
            System.out.println("Getting User by Phone Number: " + user5.toString() + "\n");
        }

        Car car1 = addCar(1L, "Mercedes", "S-class", "black",
                2020, 1500, true);
        if (carService.saveCar(car1)) {
            System.out.println("Added car: " + car1.toString() + "\n");
        }

        Car car2 = addCar(2L, "Toyota", "Camry", "white",
                2010, 1100, false);
        if (carService.saveCar(car2)) {
            System.out.println("Added car: " + car2.toString() + "\n");
        }

        Request request1 = addRequest(1L, user1, car1);
        if (requestService.saveRequest(request1)) {
            System.out.println("Added request: " + request1.toString() + "\n");
        }

        Request request2 = addRequest(2L, user1, car2);
        if (requestService.saveRequest(request2)) {
            System.out.println("Added request: " + request2.toString() + "\n");
        }

        Request request3 = addRequest(3L, user2, car1);
        if (requestService.saveRequest(request3)) {
            System.out.println("Added request: " + request3.toString() + "\n");
        }

        printListOfUsers();
        printListOfCars();
        printListOfRequests();
        printListOfRequestsByUser(user1);
        printListOfRequestsByCar(car1);
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

    private Request addRequest(Long id, User user, Car car) {
        Request request = new Request();

        request.setId(id);
        request.setUserId(user.getId());
        request.setCarId(car.getId());
        request.setRequestTime(new Date(System.currentTimeMillis()));

        return request;
    }

    private void printListOfUsers() {
        System.out.println("USERS");
        List<User> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            for (User u : users) {
                System.out.println(u.toString());
            }
        }
        System.out.println();
    }

    private void printListOfCars() {
        System.out.println("CARS");
        List<Car> cars = carService.getAllCars();
        if (!cars.isEmpty()) {
            for (Car c : cars) {
                System.out.println(c.toString());
            }
        }
        System.out.println();
    }

    private void printListOfRequests() {
        System.out.println("REQUESTS");
        List<Request> requests = requestService.getAllRequests();
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                System.out.println(r.toString());
            }
        }
        System.out.println();
    }

    private void printListOfRequestsByUser(User user) {
        System.out.println("REQUESTS BY USER " + user);
        List<Request> requests = requestService.getRequestsByUser(user);
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                System.out.println(r.toString());
            }
        }
        System.out.println();
    }

    private void printListOfRequestsByCar(Car car) {
        System.out.println("REQUESTS BY CAR " + car);
        List<Request> requests = requestService.getRequestsByCar(car);
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                System.out.println(r.toString());
            }
        }
        System.out.println();
    }
}
