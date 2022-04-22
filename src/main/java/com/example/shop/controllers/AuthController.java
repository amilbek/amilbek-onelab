package com.example.shop.controllers;

import com.example.shop.constants.Constants;
import com.example.shop.payload.request.LoginRequest;
import com.example.shop.payload.request.SignupRequest;
import com.example.shop.payload.responce.JWTTokenSuccessResponse;
import com.example.shop.security.JWTTokenProvider;
import com.example.shop.constants.SecurityConstants;
import com.example.shop.services.Services;
import com.example.shop.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ResponseErrorValidation responseErrorValidation;
    private final Services userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;


    @Autowired
    public AuthController(ResponseErrorValidation responseErrorValidation,
                          Services userService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.responseErrorValidation = responseErrorValidation;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     "username": "stark",
     "password": "123"
     */
    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult)  {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    /**
        "firstName": "Tony",
        "lastName": "Stark",
        "phoneNumber": "87775553366",
        "birthDate": "1972-02-05",
        "username": "stark",
        "password": "123",
        "isAdmin": true
     */

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        boolean result = userService.saveUser(signupRequest);
        if (!result) {
            return new ResponseEntity<>(Constants.REGISTRATION_FAILED, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(Constants.REGISTRATION_SUCCEED);
    }
}
