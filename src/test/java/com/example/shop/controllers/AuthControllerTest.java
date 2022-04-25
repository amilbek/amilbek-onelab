package com.example.shop.controllers;

import com.example.shop.constants.Constants;
import com.example.shop.payload.request.LoginRequest;
import com.example.shop.payload.request.SignupRequest;
import com.example.shop.payload.responce.JWTTokenSuccessResponse;
import com.example.shop.security.JWTTokenProvider;
import com.example.shop.services.Services;
import com.example.shop.validations.ResponseErrorValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController sut;

    @Mock
    Services services;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    BindingResult bindingResult;

    @Mock
    ResponseErrorValidation responseErrorValidation;

    @Mock
    JWTTokenProvider jwtTokenProvider;

    @Mock
    Authentication authentication;

    @Test
    void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        ResponseEntity<Object> expected =
                ResponseEntity.ok(new JWTTokenSuccessResponse(true, "Bearer jwt"));

        when(responseErrorValidation.mapValidationService(bindingResult)).thenReturn(null);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("jwt");

        ResponseEntity<Object> actual = sut.authenticateUser(loginRequest, bindingResult);

        assertEquals(expected, actual);
    }

    @Test
    void registerUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("firstName");
        signupRequest.setLastName("lastName");
        signupRequest.setPhoneNumber("phoneNumber");
        LocalDate date = LocalDate.of(2002, 11, 13);
        signupRequest.setBirthDate(date);
        signupRequest.setUsername("username");
        signupRequest.setPassword("password");
        signupRequest.setIsAdmin(false);

        when(responseErrorValidation.mapValidationService(bindingResult)).thenReturn(null);
        when(services.saveUser(signupRequest)).thenReturn(true);

        ResponseEntity<Object> expected = ResponseEntity.ok(Constants.REGISTRATION_SUCCEED);
        ResponseEntity<Object> actual = sut.registerUser(signupRequest, bindingResult);

        assertEquals(expected, actual);
    }
}