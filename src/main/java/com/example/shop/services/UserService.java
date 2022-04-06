package com.example.shop.services;

import com.example.shop.dto.User;
import com.example.shop.repository.impl.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(User userIn) {
        User userCheck = userRepository.getUserByPhoneNumber(userIn.getPhoneNumber());
        if (userCheck != null) {
            System.out.println("User already exists with phone number " + userCheck.getPhoneNumber());
            System.out.println("Already registered " + userIn.toString() + "\n");
            return false;
        }

        User user = new User();
        user.setFirstName(userIn.getFirstName());
        user.setLastName(userIn.getLastName());
        user.setPhoneNumber(userIn.getPhoneNumber());
        user.setBirthDate(userIn.getBirthDate());
        userRepository.saveUser(user);
        return true;
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            System.out.println("No User Found with phone number " + phoneNumber + "\n");
            return null;
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
