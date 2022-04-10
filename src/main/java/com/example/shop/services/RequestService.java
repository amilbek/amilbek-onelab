package com.example.shop.services;

import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.repository.impl.IRequestRepository;
import com.example.shop.repository.impl.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final IUserRepository userRepository;
    private final IRequestRepository requestRepository;

    @Autowired
    public RequestService(IUserRepository userRepository, IRequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public boolean saveRequest(Request requestIn) {
        User user = userRepository.getUserById(requestIn.getUserId());
        if (user == null) {
            System.out.println("User does not exist to make request\n");
            return false;
        }
        Request request = new Request();
        request.setId(requestIn.getId());
        request.setUserId(requestIn.getUserId());
        request.setCarId(requestIn.getCarId());
        request.setRequestTime(requestIn.getRequestTime());

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
