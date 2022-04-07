package com.example.shop.dto;

import lombok.Data;

import java.time.Year;

@Data
public class Car {

    private Long carNumber;
    private String brand;
    private String model;
    private String color;
    private Year productionYear;
    private double price;
    private boolean isAvailable;

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "carNumber=" + carNumber +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", productionYear=" + productionYear +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
