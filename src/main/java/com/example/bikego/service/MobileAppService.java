package com.example.bikego.service;

import com.example.bikego.dto.RentBikeForm;
import com.example.bikego.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface MobileAppService {
    ResponseEntity<ResponseObject> getAllBikeAvailable(int pageNumber, int pageSize);
    ResponseEntity<ResponseObject> rentBike(RentBikeForm rentBikeForm);

    ResponseEntity<ResponseObject> finish(String uid, Long bikeId);

    ResponseEntity<ResponseObject> getRentHistory(String uid,int pageNumber, int pageSize, String status);

    ResponseEntity<ResponseObject> cancel(String uid, Long bikeId);
}
