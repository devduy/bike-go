package com.example.bikego.service.impl;

import com.example.bikego.dto.BikeColorDTO;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.Bike.BikeColor;
import com.example.bikego.repository.BikeColorRepository;
import com.example.bikego.service.BikeColorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeColorServiceImpl implements BikeColorService {
    private final BikeColorRepository bikeColorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BikeColorServiceImpl(BikeColorRepository bikeColorRepository, ModelMapper modelMapper) {
        this.bikeColorRepository = bikeColorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBikeColor() {
        try{
            List<BikeColor> bikeColorList = bikeColorRepository.findAll();
            List<BikeColorDTO> bikeColorDTOList = new ArrayList<>();
            for (BikeColor bikeColor: bikeColorList
                 ) {
                bikeColorDTOList.add(modelMapper.map(bikeColor, BikeColorDTO.class));

            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(),"Successfully", null, bikeColorDTOList));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(),"Fail"+e.getMessage(), null, null));
        }
    }
}
