package com.example.bikego.service;

import com.example.bikego.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface OwnerShopService {
    ResponseEntity<ResponseObject> getAll();
    ResponseEntity<ResponseObject> findById(Long id);
    ResponseEntity<ResponseObject> getShopByUserId(String uid);
}
