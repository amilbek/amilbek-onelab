package com.example.shop.repo.impl;

import com.example.shop.dto.Car;
import com.example.shop.dto.Request;
import com.example.shop.dto.User;
import com.example.shop.repo.RequestRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IRequestRepository implements RequestRepository {

    private final Map<Long, Request> requests = new HashMap<>();

    @Override
    public Request saveRequest(Request request) {
        requests.put(request.getId(), request);
        return requests.get(request.getId());
    }

    @Override
    public List<Request> getAllRequests() {
        return new ArrayList<>(requests.values());
    }

    @Override
    public List<Request> getRequestsByUser(User user) {
        List<Request> requestsByUser = new ArrayList<>();
        for (Map.Entry<Long, Request> requestEntry : requests.entrySet()) {
            if (requestEntry.getValue().getUser().equals(user)) {
                requestsByUser.add(requestEntry.getValue());
            }
        }
        return requestsByUser;
    }

    @Override
    public List<Request> getRequestsByCar(Car car) {
        List<Request> requestsByCar = new ArrayList<>();
        for (Map.Entry<Long, Request> requestEntry : requests.entrySet()) {
            if (requestEntry.getValue().getCar().equals(car)) {
                requestsByCar.add(requestEntry.getValue());
            }
        }
        return requestsByCar;
    }
}
