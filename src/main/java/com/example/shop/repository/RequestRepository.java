package com.example.shop.repository;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository {

    Request saveRequest(Request request);

    List<Request> getAllRequests();

    List<Request> getRequestsByUser(User user);

    List<Request> getRequestsByCar(Car car);
}
