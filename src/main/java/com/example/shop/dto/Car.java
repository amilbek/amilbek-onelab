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

    public static CarBuilder carBuilder() {
        return new Car().new CarBuilder();
    }

    public class CarBuilder {

        private CarBuilder() {
        }

        public CarBuilder carNumber(Long carNumber) {
            Car.this.carNumber = carNumber;
            return this;
        }

        public CarBuilder brand(String brand) {
            Car.this.brand = brand;
            return this;
        }

        public CarBuilder model(String model) {
            Car.this.model = model;
            return this;
        }

        public CarBuilder color(String color) {
            Car.this.color = color;
            return this;
        }

        public CarBuilder year(Year date) {
            Car.this.productionYear = date;
            return this;
        }

        public CarBuilder price(double price) {
            Car.this.price = price;
            return this;
        }

        public CarBuilder availability(boolean availability) {
            Car.this.isAvailable = availability;
            return this;
        }

        public Car build() {
            return Car.this;
        }
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
