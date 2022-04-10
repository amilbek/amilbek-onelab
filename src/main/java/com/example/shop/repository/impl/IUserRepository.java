package com.example.shop.repository.impl;

import com.example.shop.dao.impl.IUserDao;
import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IUserRepository implements UserRepository {

    private final IUserDao userDao;

    @Autowired
    public IUserRepository(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userDao.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
