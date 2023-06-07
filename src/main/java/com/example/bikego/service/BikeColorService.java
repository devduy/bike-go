package com.example.bikego.service;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.Bike.BikeColor;
import org.springframework.http.ResponseEntity;

public interface BikeColorService {
    ResponseEntity<ResponseObject> getAllBikeColor();
}
