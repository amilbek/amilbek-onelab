package com.example.shop.repository.impl;

import com.example.shop.dao.impl.IRequestDao;
import com.example.shop.entity.Car;
import com.example.shop.entity.Request;
import com.example.shop.entity.User;
import com.example.shop.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IRequestRepository implements RequestRepository {

    private final IRequestDao requestDao;

    @Autowired
    public IRequestRepository(IRequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public Request saveRequest(Request request) {
        return requestDao.saveRequest(request);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestDao.getAllRequests();
    }

    @Override
    public List<Request> getRequestsByUser(User user) {
        return requestDao.getRequestsByUser(user);
    }

    @Override
    public List<Request> getRequestsByCar(Car car) {
        return requestDao.getRequestsByCar(car);
    }
}
