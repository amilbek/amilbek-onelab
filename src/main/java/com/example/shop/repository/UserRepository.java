package com.example.shop.repository;

import com.example.shop.entity.User;

import java.util.List;

public interface UserRepository {

    User saveUser(User user);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);

    List<User> getAllUsers();
}
