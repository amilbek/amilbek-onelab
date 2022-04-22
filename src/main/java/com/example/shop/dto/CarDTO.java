package com.example.shop.dto;

import lombok.Data;

@Data
public class CarDTO {

    private Long id;
    private String carNumber;
    private String brand;
    private String model;
    private String color;
    private Integer productionYear;
    private Double price;
    private Boolean isAvailable;
}
