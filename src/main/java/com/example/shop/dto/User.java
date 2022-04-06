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

    public static UserBuilder userBuilder() {
        return new User().new UserBuilder();
    }

    public class UserBuilder {

        private UserBuilder() {
        }

        public UserBuilder fName(String fname) {
            User.this.firstName = fname;
            return this;
        }

        public UserBuilder lName(String lname) {
            User.this.lastName = lname;
            return this;
        }

        public UserBuilder phoneNumber(String phoneNumber) {
            User.this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder date(LocalDate date) {
            User.this.birthDate = date;
            return this;
        }

        public User build() {
            return User.this;
        }
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
