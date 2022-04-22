package com.example.shop.services;

import com.example.shop.dto.CarDTO;
import com.example.shop.dto.RequestDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.payload.request.SignupRequest;
import com.example.shop.repository.CarRepository;
import com.example.shop.repository.RequestRepository;
import com.example.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    Principal principal;

    @Test
    void testSaveUser() {
        SignupRequest signupRequest = addUser(
        );

        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("password");

        boolean result = sut.saveUser(signupRequest);
        assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        UserDTO userDTOExpected = addUserDTO("firstname1", "lastname1",
                "87770806565", "1982-11-13", "username1");
        User expected = userDtoToUser(userDTOExpected);

        when(principal.getName()).thenReturn("username");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        User actual = sut.updateUser(userDTOExpected, principal);
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteUser() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        when(principal.getName()).thenReturn("username");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        boolean result = sut.deleteUser(principal);
        assertTrue(result);
    }

    @Test
    void testGetCurrentUser() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User expected = userDtoToUser(userDTO);

        when(principal.getName()).thenReturn("username");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(expected));

        User actual = sut.getCurrentUser(principal);
        assertEquals(expected, actual);
    }

    @Test
    void testGetUserById() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User expected = userDtoToUser(userDTO);

        when(userRepository.findUserById(1L)).thenReturn(Optional.of(expected));

        User actual = sut.getUserById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void testGetUserByUsername() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User expected = userDtoToUser(userDTO);

        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(expected));

        User actual = sut.getUserByUsername("username");
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        SignupRequest signupRequest = addUser(
        );
        User expected = new User();
        expected.setFirstName(signupRequest.getFirstName());
        expected.setLastName(signupRequest.getLastName());
        expected.setPhoneNumber(signupRequest.getPhoneNumber());
        expected.setBirthDate(signupRequest.getBirthDate());
        expected.setUsername(signupRequest.getUsername());
        expected.setPassword(signupRequest.getPassword());

        expectedUsers.add(expected);

        sut.saveUser(signupRequest);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = sut.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testSaveCar() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);

        boolean result = sut.saveCar(carDTO);
        assertTrue(result);
    }

    @Test
    void testGetCarById() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);

        Car expected = carDtoToCar(carDTO);

        when(carRepository.findById(1L)).thenReturn(Optional.of(expected));

        Car actual = sut.getCarById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void testGetCarByCarNumber() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);

        Car expected = carDtoToCar(carDTO);

        when(carRepository.findCarByCarNumber("carNumber")).thenReturn(Optional.of(expected));

        Car actual = sut.getCarByCarNumber("carNumber");
        assertEquals(expected, actual);
    }

    @Test
    void testUpdateCar() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);
        when(carRepository.save(car)).thenReturn(car);

        CarDTO carDTOExpected = addCar("carNumber1", "brand1", "model1",
                "color1",  2120,  15100.0, true);
        Car expected = carDtoToCar(carDTOExpected);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Car actual = sut.updateCar(carDTOExpected, 1L);
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteCar() {
        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        boolean result = sut.deleteCar(1L);
        assertTrue(result);
    }

    @Test
    void getAllCars() {
        List<Car> expectedCars = new ArrayList<>();

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car expectedCar = carDtoToCar(carDTO);

        expectedCars.add(expectedCar);

        sut.saveCar(carDTO);

        when(carRepository.findAll()).thenReturn(expectedCars);

        List<Car> actualCars = sut.getAllCars();
        assertEquals(expectedCars, actualCars);
    }

    @Test
    void testSaveRequest() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);
        when(principal.getName()).thenReturn("username");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);
        when(carRepository.findCarByCarNumber("carNumber")).thenReturn(Optional.of(car));

        LocalDateTime localDateTime = LocalDateTime.now();

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setCarNumber(carDTO.getCarNumber());
        requestDTO.setRequestTime(localDateTime);

        Request request = addRequest(user, car, localDateTime);

        when(requestRepository.save(request)).thenReturn(request);

        boolean result = sut.saveRequest(requestDTO, principal);
        assertTrue(result);
    }

    @Test
    void testGetRequestsByRequestTime() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        LocalDateTime localDateTime1 = LocalDateTime.now();

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setCarNumber(carDTO.getCarNumber());
        requestDTO.setRequestTime(localDateTime1);

        Request request1 = addRequest(user, car, localDateTime1);

        LocalDateTime localDateTime2 = LocalDateTime.now();
        Request request2 = addRequest(user, car, localDateTime2);

        List<Request> expected = new ArrayList<>();
        expected.add(request2);
        expected.add(request1);

        when(requestRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Request::getRequestTime).reversed())
                .collect(Collectors.toList())).thenReturn(expected);

        List<Request> actual = sut.getRequestByRequestTime();
        assertEquals(expected, actual);
    }

    @Test
    void testGetRequestByUser() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        LocalDateTime localDateTime1 = LocalDateTime.now();

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setCarNumber(carDTO.getCarNumber());
        requestDTO.setRequestTime(localDateTime1);

        Request request = addRequest(user, car, localDateTime1);

        List<Request> expected = new ArrayList<>();
        expected.add(request);

        when(requestRepository.findAll()
                .stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList())).thenReturn(expected);
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        List<Request> actual = sut.getRequestsByUser("username");
        assertEquals(expected, actual);
    }
    @Test
    void testGetRequestByCar() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber", "brand", "model",
                "color",  2020,  1500.0, true);
        Car car = carDtoToCar(carDTO);

        LocalDateTime localDateTime1 = LocalDateTime.now();

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setCarNumber(carDTO.getCarNumber());
        requestDTO.setRequestTime(localDateTime1);

        Request request = addRequest(user, car, localDateTime1);

        List<Request> expected = new ArrayList<>();
        expected.add(request);

        when(requestRepository.findAll()
                .stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList())).thenReturn(expected);
        when(carRepository.findCarByCarNumber("carNumber")).thenReturn(Optional.of(car));

        List<Request> actual = sut.getRequestsByCar("carNumber");
        assertEquals(expected, actual);
    }

    @Test
    void testGetRequestByCurrentUser() {
        UserDTO userDTO = addUserDTO("firstName", "lastName",
                "87770801565", "1982-10-13", "username1");
        User user = userDtoToUser(userDTO);

        CarDTO carDTO = addCar("carNumber1", "brand1", "model1",
                "color1",  2010,  2500.0, false);
        Car car = carDtoToCar(carDTO);

        LocalDateTime localDateTime1 = LocalDateTime.now();

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setCarNumber(carDTO.getCarNumber());
        requestDTO.setRequestTime(localDateTime1);

        Request request = addRequest(user, car, localDateTime1);

        List<Request> expected = new ArrayList<>();
        expected.add(request);

        when(principal.getName()).thenReturn("username");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(requestRepository.findAll()
                .stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList())).thenReturn(expected);

        List<Request> actual = sut.getRequestsByCurrentUser(principal);
        assertEquals(expected, actual);
    }

    private SignupRequest addUser() {
        SignupRequest user = new SignupRequest();
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPhoneNumber("87770806565");
        LocalDate date = LocalDate.parse("1982-11-13");
        user.setBirthDate(date);
        user.setUsername("username");
        user.setPassword("password");
        user.setIsAdmin(true);
        return user;
    }

    private UserDTO addUserDTO(String firstName, String lastName, String phoneNumber, String birthDate,
                               String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPhoneNumber(phoneNumber);
        LocalDate date = LocalDate.parse(birthDate);
        userDTO.setBirthDate(date);
        userDTO.setUsername(username);
        return userDTO;
    }

    private User userDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBirthDate(userDTO.getBirthDate());
        user.setUsername(userDTO.getUsername());
        return user;
    }

    private CarDTO addCar(String carNumber, String brand, String model,
                          String color, Integer productionYear, Double price,
                          Boolean isAvailable) {
        CarDTO carDTO = new CarDTO();

        carDTO.setCarNumber(carNumber);
        carDTO.setBrand(brand);
        carDTO.setModel(model);
        carDTO.setColor(color);
        carDTO.setProductionYear(productionYear);
        carDTO.setPrice(price);
        carDTO.setIsAvailable(isAvailable);

        return carDTO;
    }

    private Car carDtoToCar(CarDTO carDTO) {
        Car car = new Car();

        car.setCarNumber(carDTO.getCarNumber());
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setColor(carDTO.getColor());
        car.setPrice(carDTO.getPrice());
        car.setProductionYear(carDTO.getProductionYear());
        car.setIsAvailable(carDTO.getIsAvailable());

        return car;
    }

    private Request addRequest(User user, Car car, LocalDateTime localDateTime) {
        Request request = new Request();
        request.setUser(user);
        request.setCar(car);
        request.setRequestTime(localDateTime);
        request.setIsAccepted(false);
        return request;
    }
}