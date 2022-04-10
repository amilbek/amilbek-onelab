package com.example.shop.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Request {

    private Long id;
    private Long userId;
    private Long carId;
    private Date requestTime;

    public Request() {
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", user=" + userId +
                ", car=" + carId +
                ", requestTime=" + requestTime +
                '}';
    }
}
