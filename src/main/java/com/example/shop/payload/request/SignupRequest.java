package com.example.shop.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String username;
    private String password;
    private Boolean isAdmin;
}
