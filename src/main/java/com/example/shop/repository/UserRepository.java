package com.example.shop.repository;

import com.example.shop.dto.User;

import java.util.List;

public interface UserRepository {

    User saveUser(User user);

    User getUserByPhoneNumber(String phoneNumber);

    List<User> getAllUsers();
}
