package com.example.shop;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Application {

    private final Services services;

    @Autowired
    public Application(Services services) {
        this.services = services;
    }

    public void start() {
        User user1 = addUser("John", "Wick", "87770806565",
                "13/11/1982");
        if (services.saveUser(user1)) {
            System.out.println("Created user: " + user1.toString() + "\n");
        }

        User updateUser1 = addUser("Steven", "Strange", "87770806565",
                "13/11/1982");

        updateUser1 = services.updateUser(updateUser1, 1L);
        if (updateUser1 != null) {
            System.out.println("Updated user: " + updateUser1.toString() + "\n");
        }

        User user2 = addUser("John", "Snow", "87770806565",
                "13/11/1972");
        if (services.saveUser(user2)) {
            System.out.println("Created user: " + user2.toString() + "\n");
        }

        User user3 = addUser("Sam", "Winchester", "87770806566",
                "13/11/1992");
        if (services.saveUser(user3)) {
            System.out.println("Created user: " + user3.toString() + "\n");
        }

        User user4 = services.getUserByPhoneNumber("87770806565");
        if (user4 != null) {
            System.out.println("Getting User by Phone Number: " + user4.toString() + "\n");
        }

        User user5 = services.getUserByPhoneNumber("87775553366");
        if (user5 != null) {
            System.out.println("Getting User by Phone Number: " + user5.toString() + "\n");
        }

        Car car1 = addCar("Mercedes", "S-class", "black",
                2020, 1500.0, true);
        if (services.saveCar(car1)) {
            System.out.println("Added car: " + car1.toString() + "\n");
        }

        Car updateCar1 = addCar("Mercedes", "E-class", "white",
                2019, 1600.0, true);
        updateCar1 = services.updateCar(updateCar1, 1L);
        if (updateCar1 != null) {
            System.out.println("Updated user: " + updateCar1.toString() + "\n");
        }

        Car car2 = addCar("Toyota", "Camry", "white",
                2010, 1100.0, false);
        if (services.saveCar(car2)) {
            System.out.println("Added car: " + car2.toString() + "\n");
        }

        Request request1 = addRequest(1L, 1L, false);
        if (services.saveRequest(request1)) {
            System.out.println("Added request: " + request1.toString() + "\n");
        }

        Request request2 = addRequest(1L, 2L, false);
        if (services.saveRequest(request2)) {
            System.out.println("Added request: " + request2.toString() + "\n");
        }

        Request request3 = addRequest(2L, 1L, true);
        if (services.saveRequest(request3)) {
            System.out.println("Added request: " + request3.toString() + "\n");
        }

        printListOfUsers();
        printListOfCars();
        if (services.deleteUser(2L)) {
            System.out.println("User deleted with ID: " + 2 + "\n");
        }
        if (services.deleteCar(2L)) {
            System.out.println("Car deleted with ID: " + 2 + "\n");
        }
        printListOfUsers();
        printListOfCars();
        printListOfRequests();
        printListOfRequestsByUser(1L);
        printListOfRequestsByCar(1L);
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

    private Request addRequest(Long userId, Long carId, Boolean isAccepted) {
        Request request = new Request();

        User user = services.getUserById(userId);
        Car car = services.getCarById(carId);

        request.setUser(user);
        request.setCar(car);
        request.setRequestTime(new Date(System.currentTimeMillis()));
        request.setIsAccepted(isAccepted);

        return request;
    }

    private void printListOfUsers() {
        System.out.println("USERS");
        List<User> users = services.getAllUsers();
        if (!users.isEmpty()) {
            for (User u : users) {
                System.out.println(u.toString());
            }
        }
        System.out.println();
    }

    private void printListOfCars() {
        System.out.println("CARS");
        List<Car> cars = services.getAllCars();
        if (!cars.isEmpty()) {
            for (Car c : cars) {
                System.out.println(c.toString());
            }
        }
        System.out.println();
    }

    private void printListOfRequests() {
        System.out.println("REQUESTS");
        List<Request> requests = services.getRequestByRequestTime();
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                System.out.println(r.toString());
            }
        }
        System.out.println();
    }

    private void printListOfRequestsByUser(Long id) {
        User user = services.getUserById(id);
        System.out.println("REQUESTS BY USER " + user);
        List<Request> requests = services.getRequestsByUser(user);
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                System.out.println(r.toString());
            }
        }
        System.out.println();
    }

    private void printListOfRequestsByCar(Long id) {
        Car car = services.getCarById(id);
        System.out.println("REQUESTS BY CAR " + car);
        List<Request> requests = services.getRequestsByCar(car);
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                System.out.println(r.toString());
            }
        }
        System.out.println();
    }
}
