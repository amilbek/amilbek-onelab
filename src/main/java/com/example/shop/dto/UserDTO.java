package com.example.shop.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String username;
}
