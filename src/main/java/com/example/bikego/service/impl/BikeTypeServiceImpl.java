package com.example.bikego.service.impl;

import com.example.bikego.dto.BikeTypeDTO;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.Bike.BikeType;
import com.example.bikego.repository.BikeTypeRepository;
import com.example.bikego.service.BikeTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeTypeServiceImpl implements BikeTypeService {
    private final BikeTypeRepository bikeTypeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BikeTypeServiceImpl(BikeTypeRepository bikeTypeRepository, ModelMapper modelMapper) {
        this.bikeTypeRepository = bikeTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBikeType() {
        try {
            List<BikeType> bikeTypeList = bikeTypeRepository.findAll();
            List<BikeTypeDTO> bikeTypeDTOList = new ArrayList<>();
            for (BikeType bikeType: bikeTypeList
                 ) {
                bikeTypeDTOList.add(modelMapper.map(bikeType, BikeTypeDTO.class));

            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(),"Successfully", null, bikeTypeDTOList));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(),"Fail", null, null));

        }

    }
}
