package com.example.shop.controllers;

import com.example.shop.constants.Constants;
import com.example.shop.dto.UserDTO;
import com.example.shop.dto.transition.UserTransition;
import com.example.shop.entity.User;
import com.example.shop.services.Services;
import com.example.shop.validations.ResponseErrorValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController sut;

    @Mock
    Services services;

    @Mock
    UserTransition userTransition;

    @Mock
    Principal principal;

    @Mock
    ResponseErrorValidation responseErrorValidation;

    @Mock
    BindingResult bindingResult;

    @Test
    void getCurrentUser() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        when(userTransition.userToUserDTO(user)).thenReturn(userDTO);
        when(services.getCurrentUser(principal)).thenReturn(user);

        ResponseEntity<UserDTO> expected = new ResponseEntity<>(userDTO, HttpStatus.OK);

        ResponseEntity<UserDTO> actual = sut.getCurrentUser(principal);

        assertEquals(expected, actual);
    }

    @Test
    void updateUser() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        when(services.updateUser(userDTO, principal)).thenReturn(user);
        when(userTransition.userToUserDTO(user)).thenReturn(userDTO);

        ResponseEntity<Object> expected = new ResponseEntity<>(userDTO, HttpStatus.OK);
        ResponseEntity<Object> actual = sut.updateUser(userDTO, bindingResult,principal);
        assertEquals(expected, actual);
    }

    @Test
    void deleteUser() {
        when(services.deleteUser(principal)).thenReturn(true);

        ResponseEntity<Object> expected =
                new ResponseEntity<>(Constants.DELETING_USER_SUCCEED, HttpStatus.OK);

        ResponseEntity<Object> actual = sut.deleteUser(principal);
        assertEquals(expected, actual);
    }

    @Test
    void getUserById() {
        UserDTO userDTO = addUserDTO("firstname", "lastname",
                "87770806565", "1982-11-13", "username");
        User user = userDtoToUser(userDTO);

        when(services.getUserById(1L)).thenReturn(user);
        when(userTransition.userToUserDTO(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> expected = new ResponseEntity<>(userDTO, HttpStatus.OK);

        ResponseEntity<UserDTO> actual = sut.getUserById(String.valueOf(1L));

        assertEquals(expected, actual);
    }

    @Test
    void getUserByUsername() {
        UserDTO userDTO = addUserDTO("firstName", "lastName",
                "87770806561", "1982-10-13", "username1");
        User user = userDtoToUser(userDTO);

        when(services.getUserByUsername("username1")).thenReturn(user);
        when(userTransition.userToUserDTO(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> expected = new ResponseEntity<>(userDTO, HttpStatus.OK);

        ResponseEntity<UserDTO> actual = sut.getUserByUsername("username1");

        assertEquals(expected, actual);
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
}