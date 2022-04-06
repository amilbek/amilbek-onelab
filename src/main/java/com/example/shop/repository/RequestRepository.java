package com.example.shop.repository;

import com.example.shop.dto.Car;
import com.example.shop.dto.Request;
import com.example.shop.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository {

    Request saveRequest(Request request);

    List<Request> getAllRequests();

    List<Request> getRequestsByUser(User user);

    List<Request> getRequestsByCar(Car car);
}
