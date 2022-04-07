package com.example.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Request {

    private Long id;
    private User user;
    private Car car;
    private LocalDateTime requestTime;

    public Request() {
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", requestTime=" + requestTime +
                '}';
    }
}
