package com.example.shop.entity;

import lombok.Data;

@Data
public class Car {

    private Long id;
    private String brand;
    private String model;
    private String color;
    private int productionYear;
    private double price;
    private boolean isAvailable;

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", productionYear=" + productionYear +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
