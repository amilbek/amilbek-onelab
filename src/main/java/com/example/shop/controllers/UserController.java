package com.example.shop.controllers;

import com.example.shop.constants.Constants;
import com.example.shop.dto.UserDTO;
import com.example.shop.dto.transition.UserTransition;
import com.example.shop.entity.User;
import com.example.shop.services.Services;
import com.example.shop.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Services services;
    private final UserTransition userTransition;
    private final ResponseErrorValidation responseErrorValidation;


    @Autowired
    public UserController(Services services,
                          UserTransition userTransition,
                          ResponseErrorValidation responseErrorValidation) {
        this.services = services;
        this.userTransition = userTransition;
        this.responseErrorValidation = responseErrorValidation;
    }
    /**
    JWT in Authorization Header
    */
    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = services.getCurrentUser(principal);
        UserDTO userDTO = userTransition.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header &
        "firstName": "Anthony",
        "lastName": "Start",
        "phoneNumber": "87775553366",
        "birthDate": "1972-02-05",
        "username": "tony_stark"
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        User user = services.updateUser(userDTO, principal);
        UserDTO updatedUser = userTransition.userToUserDTO(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     JWT in Authorization Header
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(Principal principal) {
        boolean result = services.deleteUser(principal);
        if (!result) {
            return new ResponseEntity<>(Constants.DELETING_USER_FAILED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Constants.DELETING_USER_SUCCEED, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header & ID (1) in Path Variables Params
    */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") String id) {
        User user = services.getUserById(Long.parseLong(id));
        UserDTO userDTO = userTransition.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     JWT in Authorization Header & username (tony_stark) in Path Variables Params
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable(value = "username") String username) {
        User user = services.getUserByUsername(username);
        UserDTO userDTO = userTransition.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
