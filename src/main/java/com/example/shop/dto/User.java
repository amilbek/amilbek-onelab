package com.example.shop.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
