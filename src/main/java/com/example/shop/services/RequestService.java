package com.example.shop.services;

import com.example.shop.dto.Car;
import com.example.shop.dto.Request;
import com.example.shop.dto.User;
import com.example.shop.repo.impl.IRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {

    private final IRequestRepository requestRepository;

    @Autowired
    public RequestService(IRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public boolean saveRequest(Request requestIn) {
        Request request = new Request();
        request.setId(requestIn.getId());
        request.setUser(requestIn.getUser());
        request.setCar(requestIn.getCar());
        request.setRequestTime(LocalDateTime.now());

        requestRepository.saveRequest(request);

        return true;
    }

    public List<Request> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    public List<Request> getRequestsByUser(User user) {
        return requestRepository.getRequestsByUser(user);
    }

    public List<Request> getRequestsByCar(Car car) {
        return requestRepository.getRequestsByCar(car);
    }
}
