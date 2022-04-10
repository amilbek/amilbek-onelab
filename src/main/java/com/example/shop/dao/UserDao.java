package com.example.shop.dao;

import com.example.shop.entity.User;

import java.util.List;

public interface UserDao {

    User save(User user);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);

    List<User> getAllUsers();
}
