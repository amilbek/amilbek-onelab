package com.example.shop.services;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.repository.CarRepository;
import com.example.shop.repository.RequestRepository;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Services {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public Services(UserRepository userRepository,
                    CarRepository carRepository,
                    RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.requestRepository = requestRepository;
    }

    public boolean saveUser(User userIn) {
        User userCheck = userRepository.findUserByPhoneNumber(userIn.getPhoneNumber());
        if (userCheck != null) {
            System.out.println("User already exists with phone number " + userIn.getPhoneNumber());
            System.out.println("Already registered " + userCheck.toString() + "\n");
            return false;
        }
        User user = new User();
        user.setFirstName(userIn.getFirstName());
        user.setLastName(userIn.getLastName());
        user.setPhoneNumber(userIn.getPhoneNumber());
        user.setBirthDate(userIn.getBirthDate());
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public User getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null) {
            System.out.println("No User Found with phone number " + phoneNumber + "\n");
            return null;
        }
        return user;
    }

    public User updateUser(User userIn, Long userId) {
        User updateUser = userRepository.findById(userId).orElse(null);
        if (updateUser == null) {
            System.out.println("No User Found with ID " + userId + "\n");
            return null;
        }
        updateUser.setFirstName(userIn.getFirstName());
        updateUser.setLastName(userIn.getLastName());
        updateUser.setBirthDate(userIn.getBirthDate());
        userRepository.save(updateUser);
        return updateUser;
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            System.out.println("No User Found with ID " + id + "\n");
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean saveCar(Car carIn) {
        Car car = new Car();
        car.setId(carIn.getId());
        car.setBrand(carIn.getBrand());
        car.setModel(carIn.getModel());
        car.setColor(carIn.getColor());
        car.setProductionYear(carIn.getProductionYear());
        car.setPrice(carIn.getPrice());
        car.setIsAvailable(carIn.getIsAvailable());
        carRepository.save(car);
        return true;
    }

    @Transactional(readOnly = true)
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car updateCar(Car carIn, Long carId) {
        Car carCheck = carRepository.findById(carId).orElse(null);
        if (carCheck == null) {
            System.out.println("No Car Found with ID " + carId + "\n");
            return null;
        }
        carCheck.setBrand(carIn.getBrand());
        carCheck.setModel(carIn.getModel());
        carCheck.setColor(carIn.getColor());
        carCheck.setProductionYear(carIn.getProductionYear());
        carCheck.setPrice(carIn.getPrice());
        carCheck.setIsAvailable(carIn.getIsAvailable());
        carRepository.save(carCheck);
        return carCheck;
    }

    public boolean deleteCar(Long id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            System.out.println("No Car Found with ID " + id + "\n");
            return false;
        }
        carRepository.delete(car);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public boolean saveRequest(Request requestIn) {
        User user = userRepository.findById(requestIn.getUser().getId()).orElse(null);
        if (user == null) {
            System.out.println("User does not exist to make request\n");
            return false;
        }
        Request request = new Request();
        request.setId(requestIn.getId());
        request.setUser(requestIn.getUser());
        request.setCar(requestIn.getCar());
        request.setRequestTime(requestIn.getRequestTime());
        request.setIsAccepted(requestIn.getIsAccepted());
        requestRepository.save(request);
        return true;
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestByRequestTime() {
        return requestRepository.findAllByOrderByRequestTimeDesc();
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestsByUser(User user) {
        return requestRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestsByCar(Car car) {
        return requestRepository.findAllByCar(car);
    }
}
