package com.example.bikego.service.impl;

import com.example.bikego.dto.BikeBrandDTO;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.Bike.BikeBrand;
import com.example.bikego.repository.BikeBrandRepository;
import com.example.bikego.service.BikeBrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeBrandServiceImpl implements BikeBrandService {
    private final BikeBrandRepository bikeBrandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BikeBrandServiceImpl(BikeBrandRepository bikeBrandRepository, ModelMapper modelMapper) {
        this.bikeBrandRepository = bikeBrandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBikeBrand() {
        try {
            List<BikeBrand> bikeBrandList = bikeBrandRepository.findAll();
            List<BikeBrandDTO> bikeBrandDTOList = new ArrayList<>();
            for (BikeBrand bikeBrand: bikeBrandList
                 ) {
                bikeBrandDTOList.add(modelMapper.map(bikeBrand, BikeBrandDTO.class));

            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(),"Successfully", null, bikeBrandDTOList));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(),"Fail", null, null));

        }
    }
}
