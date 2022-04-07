package com.example.shop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.shop.dto.User;
import com.example.shop.repository.impl.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();

        when(userRepository.saveUser(user)).thenReturn(user);

        boolean result = userService.createUser(user);
        assertTrue(result);
    }

    @Test
    void testGetUserByPhoneNumber() {
        User expected = User.userBuilder()
                .fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();

        when(userRepository.getUserByPhoneNumber(expected.getPhoneNumber())).thenReturn(expected);

        User actual = userService.getUserByPhoneNumber(expected.getPhoneNumber());

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();

        User user1 = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446655")
                .date(LocalDate.parse("2020-11-13"))
                .build();
        expectedUsers.add(user1);
        userService.createUser(user1);

        User user2 = User.userBuilder().
                fName("firstName")
                .lName("lastName")
                .phoneNumber("87774446656")
                .date(LocalDate.parse("2020-11-13"))
                .build();
        expectedUsers.add(user2);
        userService.createUser(user2);

        when(userRepository.getAllUsers()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();
        assertEquals(expectedUsers, actualUsers);
    }
}