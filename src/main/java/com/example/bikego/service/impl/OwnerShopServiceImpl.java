package com.example.bikego.service.impl;

import com.example.bikego.dto.OwnerShopDTO;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.OwnerShop;
import com.example.bikego.repository.OwnerShopRepository;
import com.example.bikego.service.OwnerShopService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class OwnerShopServiceImpl implements OwnerShopService {
    private final OwnerShopRepository ownerShopRepository;
    private final ModelMapper modelMapper;

    public OwnerShopServiceImpl(OwnerShopRepository ownerShopRepository, ModelMapper modelMapper) {
        this.ownerShopRepository = ownerShopRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getAll() {
        try {
            List<OwnerShop> ownerShops = ownerShopRepository.findAll();
            List<OwnerShopDTO> ownerShopDTOS = ownerShops.stream().map(this::convertToDTO).toList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null, ownerShopDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> findById( Long id) {
        try {
            OwnerShop ownerShop = ownerShopRepository.findById(id).orElseThrow(() -> new ValidationException("OwnerShop is not existed"));
            OwnerShopDTO ownerShopDTO = convertToDTO(ownerShop);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null, ownerShopDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));
        }
    }

    private OwnerShopDTO convertToDTO(OwnerShop ownerShop) {
        if(ownerShop == null) {
            return null;
        }
        return modelMapper.map(ownerShop, OwnerShopDTO.class);

    }
}
