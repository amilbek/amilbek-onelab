package com.example.shop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.shop.entity.User;
import com.example.shop.repository.impl.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = addUser(1L, "John", "Wick", "87770806565",
                "13/11/1982");

        when(userRepository.saveUser(user)).thenReturn(user);

        boolean result = userService.createUser(user);
        assertTrue(result);
    }

    @Test
    void testGetUserByPhoneNumber() {
        User expected = addUser(1L, "Tony", "Stark", "87770806555",
                "13/11/1983");

        when(userRepository.getUserByPhoneNumber(expected.getPhoneNumber())).thenReturn(expected);

        User actual = userService.getUserByPhoneNumber("87770806555");

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();

        User expectedUser1 = addUser(1L, "John", "Wick", "87770806565",
                "13/11/1982");

        User expectedUser2 = addUser(2L, "Tony", "Stark", "87770806555",
                "13/11/1983");

        expectedUsers.add(expectedUser1);
        expectedUsers.add(expectedUser2);

        userService.createUser(expectedUser1);
        userService.createUser(expectedUser2);

        when(userRepository.getAllUsers()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAllUsers();
        assertEquals(expectedUsers, actualUsers);
    }

    private User addUser(Long id, String firstName, String lastName, String phoneNumber, String birthDate) {
        User user = new User();
        try {
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
            user.setBirthDate(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}