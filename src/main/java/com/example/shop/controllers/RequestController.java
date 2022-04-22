package com.example.shop.controllers;

import com.example.shop.dto.RequestDTO;
import com.example.shop.dto.transition.RequestTransition;
import com.example.shop.entity.Request;
import com.example.shop.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final Services services;
    private final RequestTransition requestTransition;

    @Autowired
    public RequestController(Services services,
                             RequestTransition requestTransition) {
        this.services = services;
        this.requestTransition = requestTransition;
    }

    /**
    JWT in Authorization Header
        "carNumber": "080QQQ08"
     */
    @PostMapping("/create")
    public ResponseEntity<RequestDTO> createRequest(@Valid @RequestBody RequestDTO requestDTO,
                                                Principal principal) {
        services.saveRequest(requestDTO, principal);
        return new ResponseEntity<>(requestDTO, HttpStatus.OK);
    }

    /**
    JWT in Authorization Header
     */
    @GetMapping("/history")
    public ResponseEntity<List<RequestDTO>> getAllRequestsByCurrentUser(Principal principal) {
        List<Request> requests = services.getRequestsByCurrentUser(principal);

        List<RequestDTO> requestDTOList = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO requestDTO = requestTransition.requestToRequestDTO(request);
            requestDTOList.add(requestDTO);
        }

        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }
}
