package com.example.shop.dto.transition;

import com.example.shop.dto.RequestDTO;
import com.example.shop.entity.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestTransition {

    public RequestDTO requestToRequestDTO(Request request) {
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setUsername(request.getUser().getUsername());
        requestDTO.setCarNumber(request.getCar().getCarNumber());
        requestDTO.setRequestTime(request.getRequestTime());
        requestDTO.setIsAccepted(request.getIsAccepted());

        return requestDTO;
    }
}
