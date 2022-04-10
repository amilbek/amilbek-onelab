package com.example.shop.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthDate;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
