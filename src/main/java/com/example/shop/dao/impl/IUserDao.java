package com.example.shop.dao.impl;

import com.example.shop.dao.UserDao;
import com.example.shop.dao.rows.UserRowMapper;
import com.example.shop.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IUserDao implements UserDao, InitializingBean {

    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_USER_QUERY = "INSERT INTO " +
            "users(id, first_name, last_name, phone_number, birth_date) VALUES(?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_PHONE_NUMBER_QUERY = "SELECT * FROM users WHERE phone_number = ?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";

    @Autowired
    public IUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update(SAVE_USER_QUERY, user.getId(), user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), user.getBirthDate());
        return user;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return jdbcTemplate.query(GET_USER_BY_PHONE_NUMBER_QUERY, new UserRowMapper(), phoneNumber)
                .stream().findAny().orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return jdbcTemplate.query(GET_USER_BY_ID_QUERY, new UserRowMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USERS_QUERY, new UserRowMapper());
    }

    @Override
    public void afterPropertiesSet() {
        if (jdbcTemplate == null) {
            throw new NullPointerException("JDBC Template is null");
        }
    }
}
