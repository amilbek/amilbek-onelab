package com.example.shop.dao;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;

import java.util.List;

public interface RequestDao {

    Request saveRequest(Request request);

    List<Request> getAllRequests();

    List<Request> getRequestsByUser(User user);

    List<Request> getRequestsByCar(Car car);
}
