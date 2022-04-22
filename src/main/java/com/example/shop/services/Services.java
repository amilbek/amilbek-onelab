package com.example.shop.services;

import com.example.shop.dto.CarDTO;
import com.example.shop.dto.RequestDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.exception.NotFoundException;
import com.example.shop.entity.enums.ERole;
import com.example.shop.payload.request.SignupRequest;
import com.example.shop.repository.CarRepository;
import com.example.shop.repository.RequestRepository;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Services {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RequestRepository requestRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public Services(UserRepository userRepository,
                    CarRepository carRepository,
                    RequestRepository requestRepository,
                    BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(SignupRequest userIn) {
        User userByPhoneNumber = userRepository.findUserByPhoneNumber(userIn.getPhoneNumber())
                .orElse(null);
        User userByUsername = userRepository.findUserByUsername(userIn.getUsername())
                .orElse(null);
        if (userByPhoneNumber != null || userByUsername != null) {
            return false;
        }
        User user = new User();
        user.setFirstName(userIn.getFirstName());
        user.setLastName(userIn.getLastName());
        user.setPhoneNumber(userIn.getPhoneNumber());
        user.setBirthDate(userIn.getBirthDate());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findUserById(id).
                orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
        return user;
    }

    public boolean deleteUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        userRepository.delete(user);
        return true;
    }

    public boolean saveCar(CarDTO carIn) {
        Car carByCarNumber = carRepository.findCarByCarNumber(carIn.getCarNumber()).orElse(null);
        if (carByCarNumber != null) {
            return false;
        }
        Car car = setCarInfo(new Car(), carIn);
        carRepository.save(car);
        return true;
    }

    @Transactional(readOnly = true)
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car updateCar(CarDTO carDTO, Long carId) {
        Car car = carRepository.findById(carId).
                orElseThrow(() -> new NotFoundException("Car not found"));
        Car updatedCar = setCarInfo(car, carDTO);
        carRepository.save(updatedCar);
        return updatedCar;
    }

    public boolean deleteCar(Long id) {
        Car car = carRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Car not found"));
        carRepository.delete(car);
        return true;
    }

    public boolean saveRequest(RequestDTO requestIn, Principal principal) {
        User user = getUserByPrincipal(principal);
        Car car = getCarByCarNumber(requestIn.getCarNumber());
        Request request = new Request();
        request.setUser(user);
        request.setCar(car);
        request.setRequestTime(requestIn.getRequestTime() == null ?
                LocalDateTime.now() : requestIn.getRequestTime());
        request.setIsAccepted(false);
        requestRepository.save(request);
        return true;
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Car getCarByCarNumber(String carNumber) {
        return carRepository.findCarByCarNumber(carNumber)
                .orElseThrow(() -> new NotFoundException("Car not found"));
    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestByRequestTime() {
        return requestRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Request::getRequestTime).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestsByCurrentUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return requestRepository.findAll()
                .stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestsByUser(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return requestRepository.findAll()
                .stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Request> getRequestsByCar(String carNumber) {
        Car car = carRepository.findCarByCarNumber(carNumber)
                .orElseThrow(() -> new NotFoundException("Car not found"));
        return requestRepository.findAll()
                .stream()
                .filter(r -> r.getCar().equals(car))
                .collect(Collectors.toList());
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }

    private Car setCarInfo(Car car, CarDTO carDTO) {
        car.setCarNumber(carDTO.getCarNumber());
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setColor(carDTO.getColor());
        car.setProductionYear(carDTO.getProductionYear());
        car.setPrice(carDTO.getPrice());
        car.setIsAvailable(carDTO.getIsAvailable());
        return car;
    }
}
