package com.example.bikego.service.impl;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.Bike.BikeStatus;
import com.example.bikego.repository.BikeStatusRepository;
import com.example.bikego.service.BikeStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeStatusServiceImpl implements BikeStatusService {
    private final BikeStatusRepository bikeStatusRepository;

    public BikeStatusServiceImpl(BikeStatusRepository bikeStatusRepository) {
        this.bikeStatusRepository = bikeStatusRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBikeStatus() {
        List<BikeStatus> bikeStatusList= bikeStatusRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(),"Successfully", null, bikeStatusList));
    }
}
