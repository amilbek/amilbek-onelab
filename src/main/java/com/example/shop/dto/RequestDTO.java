package com.example.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDTO {

    private Long id;
    private Boolean isAccepted;
    private String username;
    private String carNumber;
    private LocalDateTime requestTime;
}
