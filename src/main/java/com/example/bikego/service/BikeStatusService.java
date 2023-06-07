package com.example.bikego.service;

import com.example.bikego.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BikeStatusService {
    ResponseEntity<ResponseObject> getAllBikeStatus();
}
