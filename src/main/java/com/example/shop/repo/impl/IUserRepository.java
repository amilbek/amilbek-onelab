package com.example.shop.repo.impl;

import com.example.shop.dto.User;
import com.example.shop.repo.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IUserRepository implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User saveUser(User user) {
        users.put(user.getPhoneNumber(), user);
        return users.get(user.getPhoneNumber());
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return users.get(phoneNumber);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
