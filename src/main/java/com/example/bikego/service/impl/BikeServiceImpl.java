package com.example.bikego.service.impl;

import com.example.bikego.dto.*;
import com.example.bikego.entity.Bike.*;
import com.example.bikego.entity.OwnerShop;
import com.example.bikego.entity.User;
import com.example.bikego.repository.*;
import com.example.bikego.service.BikeService;
import com.example.bikego.service.FireBaseImgService;
import com.example.bikego.service.UserService;
import com.example.bikego.validation.ValidationOfRequestForGetBike;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;
    private final BikeBrandRepository bikeBrandRepository;
    private final BikeTypeRepository bikeTypeRepository;
    private final BikeColorRepository bikeColorRepository;
    private final OwnerShopRepository ownerShopRepository;
    private final BikeStatusRepository bikeStatusRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    private final UserServiceImpl userServiceImpl;
    private final FireBaseImgService fireBaseImgService;
    private final BikeImageRepository bikeImageRepository;

    private final OwnerShopServiceImpl ownerShopService;

    private final ValidationOfRequestForGetBike validationOfRequestForGetBike;

    @Autowired
    public BikeServiceImpl(BikeRepository bikeRepository, BikeBrandRepository bikeBrandRepository,
                           BikeTypeRepository bikeTypeRepository, BikeColorRepository bikeColorRepository, OwnerShopRepository ownerShopRepository, BikeStatusRepository bikeStatusRepository, ModelMapper modelMapper, UserService userService, UserServiceImpl userServiceImpl, FireBaseImgService fireBaseImgService, BikeImageRepository bikeImageRepository, OwnerShopServiceImpl ownerShopService, ValidationOfRequestForGetBike validationOfRequestForGetBike) {
        this.bikeRepository = bikeRepository;
        this.bikeBrandRepository = bikeBrandRepository;
        this.bikeTypeRepository = bikeTypeRepository;
        this.bikeColorRepository = bikeColorRepository;
        this.ownerShopRepository = ownerShopRepository;
        this.bikeStatusRepository = bikeStatusRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
        this.fireBaseImgService = fireBaseImgService;
        this.bikeImageRepository = bikeImageRepository;
        this.ownerShopService = ownerShopService;
        this.validationOfRequestForGetBike = validationOfRequestForGetBike;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;


//    @Override
//    public Page<BikeCreateDTO> getFilterPaging(BikePage bikePage, BikeFilterDTO bikeFilterDTO) {
//        return bikeRepository.bikeFilterWithPaging(bikeFilterDTO, bikePage);
//    }
//
//    @Override
//    public boolean sortValidation(String sortDirection, String sortBy) {
//        boolean validation = false;
//        if (sortDirection.trim().equalsIgnoreCase("ASC") || sortDirection.trim().equalsIgnoreCase("DESC")) {
//            String[] arraysValue = {"id", "name", "rate", "numOfRide", "bikeDisplacement", "price", "bikeBrand", "bikeType"};
//            for (String type : arraysValue) {
//                if (type.equals(sortBy)) {
//                    validation = true;
//                    break;
//                }
//            }
//        }
//        return validation;
//    }

    @Override
    public Page<BikeCreateDTO> getFilterPaging(BikePage bikePage, BikeFilterDTO bikeFilterDTO) {
        return null;
    }

    @Override
    public boolean sortValidation(String sortDirection, String sortBy) {
        return false;
    }

    @Override
    public ResponseEntity<ResponseObject> createBike(String uid,BikeCreateDTO bikeCreateDTO) {
        try{
            User currentUser = userService.getCurrentUser(uid);
            Bike bike = modelMapper.map(bikeCreateDTO, Bike.class);
            BikeBrand bikeBrand = bikeBrandRepository.findByName(bikeCreateDTO.getBikeBrandName());
            User user = userService.findUserByEmail(bikeCreateDTO.getOwnerEmail());
            OwnerShop ownerShop = ownerShopRepository.findByName(user.getOwnerShop().getName());
            BikeStatus bikeStatus = bikeStatusRepository.findByName(bikeCreateDTO.getBikeStatusName());
            BikeType bikeType = bikeTypeRepository.findByName(bikeCreateDTO.getBikeTypeName());
            List<BikeColor> bikeColorList = new ArrayList<>();
            List<BikeImage> bikeImageList = new ArrayList<>();
            BikeImage bikeImage = new BikeImage();
            for (String name: bikeCreateDTO.getBikeColorName()
            ) {
                bikeColorList.add(bikeColorRepository.findByName(name));

            }

            if(ownerShop == null) {
                throw new ValidationException("Shop is not existed");
            }
            if(bikeStatus == null) {
                throw new ValidationException("Status is not existed");
            }
            if(bikeBrand == null) {
                throw new ValidationException("Bike Brand is not existed");
            }
            if(bikeType == null) {
                throw new ValidationException("Bike Type is not existed");
            }
            if(user == null) {
                throw new ValidationException("Owner is not existed");
            }

            bike.setCreatedBy(currentUser);
            bike.setUser(user);
            bike.setBikeColorList(bikeColorList);
            bike.setBikeBrand(bikeBrand);
            bike.setBikeType(bikeType);
            bike.setOwnerShop(ownerShop);
            bike.setBikeStatus(bikeStatus);
            bikeImage.setBike(bike);
            bikeImage.setImgUrl(bikeCreateDTO.getImgUrl());

            bikeImageList.add(bikeImage);
            bike.setBikeImageList(bikeImageList);
            bikeRepository.save(bike);
            bikeImageRepository.save(bikeImage);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(),"Create bike successfully", null, null));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(),"Create bike fail"+ e.getMessage(), null, null));

        }

    }

    @Override
    public ResponseEntity<ResponseObject> getAllBike() {
        try {

            List<Bike> bikeList = bikeRepository.findAllNotDeleted();
            List<BikeDTO> bikeDTOList = bikeList.stream().map(this::convertToDTO).toList();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null, bikeDTOList));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Fail"+e.getMessage(), null, null));
        }


    }

    @Override
    public ResponseEntity<ResponseObject> softDeleteBike(Long id) {
        try {
            Bike bike = bikeRepository.findById(id).orElseThrow(() -> new ValidationException("Bike is not existed"));
            bike.setDeleted(true);
            bikeRepository.save(bike);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), e.getMessage(), null, null));

        }

    }

    @Override
    public ResponseEntity<ResponseObject> updateBike(Long id, BikeUpdateDTO bikeUpdateDTO) {
        try {
            Bike bike = bikeRepository.findById(id).orElseThrow(() -> new ValidationException("Bike is not existed"));
            bike.setName(bikeUpdateDTO.getName());
            bike.setRate(bikeUpdateDTO.getRate());
            bike.setNumOfRide(bikeUpdateDTO.getNumOfRide());
            bike.setBikeDisplacement(bikeUpdateDTO.getBikeDisplacement());
            bike.setPrice(bikeUpdateDTO.getPrice());
            List<BikeColor> bikeColorList = new ArrayList<>();
            for (String name: bikeUpdateDTO.getBikeColorName()
                 ) {
                bikeColorList.add(bikeColorRepository.findByName(name));
            }
            bike.setBikeColorList(bikeColorList);
            Bike savedBike = bikeRepository.save(bike);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, convertToDTO(savedBike)));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), e.getMessage(), null, null));

        }

    }

    @Override
    public ResponseEntity<ResponseObject> filterBikeByStatus(String uid, int pageNumber, int pageSize, Long idStatus) {
        try {
            List<Bike> bikeList = new ArrayList<>();
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<Bike> bikePage;
            if(idStatus == null) {
                bikePage = bikeRepository.findAllByUserId(uid,pageable);
                System.out.println(bikePage);
            }else {
                BikeStatus bikeStatus = bikeStatusRepository.findById(idStatus).orElseThrow(() -> new ValidationException("Bike Status is not existed"));

                bikePage = bikeRepository.findByBikeStatus(bikeStatus,uid,pageable);
            }
            bikeList = bikePage.getContent();
            List<BikeDTO> bikeDTOList = bikeList.stream().map(this::convertToDTO).toList();
            Pagination pagination = new Pagination(bikePage.getNumber(),bikePage.getTotalElements(),bikePage.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Get success", pagination, bikeDTOList));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));

        }

    }

//    public List<BikeImage> addImageToBike(Bike bike, MultipartFile[] files) {
//        List<BikeImage> bikeImageList = new ArrayList<>();
//        for (MultipartFile file : files) {
//            try {
//                BikeImage bikeImage = new BikeImage();
//                String fileName = fireBaseImgService.save(file);
//                String imageUrl = fireBaseImgService.getImageUrl(fileName);
//                bikeImage.setBike(bike);
//                bikeImage.setImgUrl(imageUrl);
//                bikeImageRepository.save(bikeImage);
//                bikeImageList.add(bikeImage);
//            }catch (Exception e) {
//                return null;
//            }
//
//        }
//        return bikeImageList;
//    }

    @Override
    public ResponseEntity<ResponseObject> findById(Long id) {
        try {
            Bike bike = bikeRepository.findById(id).orElseThrow(() -> new ValidationException("Bike is not existed"));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successful", null, convertToBikeOwnerDTO(bike)));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));
        }
    }


    public BikeDTO convertToDTO(Bike bike) {
        if(bike == null) {
            return null;
        }
        BikeDTO bikeDTO = modelMapper.map(bike, BikeDTO.class);
        bikeDTO.setBikeBrandName(bike.getBikeBrand().getName());
        bikeDTO.setBikeTypeName(bike.getBikeType().getName());
//        bikeDTO.setCreateDate(String.valueOf(bike.getCreateDate()));
//        bikeDTO.setUpdateDate(String.valueOf(bike.getUpdateDate()));
        bikeDTO.setCreatedBy(bike.getCreatedBy().getFirstName() +" "+ bike.getCreatedBy().getLastName());
        bikeDTO.setOwnerName(bike.getUser().getFirstName() + " "+ bike.getUser().getLastName());
        bikeDTO.setColorsName(getBikeColorsName(bike));
//        bikeDTO.setOwnerShop(bike.getOwnerShop().getName());
//        bikeDTO.setOwnerShopAddress(bike.getOwnerShop().getAddress());
        bikeDTO.setOwnerShopDTO(ownerShopService.convertToDTO(bike.getOwnerShop()));
        bikeDTO.setImgUrl(getBikeImgUrl(bike));
        bikeDTO.setBikeStatus(bike.getBikeStatus().getName());

        return bikeDTO;
    }
    public BikeOwnerDTO convertToBikeOwnerDTO(Bike bike) {
        if(bike == null) {
            return null;
        }
        BikeOwnerDTO bikeOwnerDTO = modelMapper.map(bike, BikeOwnerDTO.class);
        bikeOwnerDTO.setBikeBrandName(bike.getBikeBrand().getName());
        bikeOwnerDTO.setBikeTypeName(bike.getBikeType().getName());
        bikeOwnerDTO.setCreatedBy(bike.getCreatedBy().getFirstName() +" "+ bike.getCreatedBy().getLastName());
        bikeOwnerDTO.setOwnerName(bike.getUser().getFirstName() + " "+ bike.getUser().getLastName());
        bikeOwnerDTO.setColorsName(getBikeColorsName(bike));
        bikeOwnerDTO.setOwnerShop(bike.getOwnerShop().getName());
        bikeOwnerDTO.setOwnerShopAddress(bike.getOwnerShop().getAddress());
        bikeOwnerDTO.setImgUrl(getBikeImgUrl(bike));
        bikeOwnerDTO.setBikeStatus(bike.getBikeStatus().getName());
        bikeOwnerDTO.setUserRentDTO(userServiceImpl.convertToUserRentDTO(bike.getRentUser()));
        return bikeOwnerDTO;

    }
    public List<String> getBikeColorsName(Bike bike) {
        List<String> bikeColors = new ArrayList<>();

            List<BikeColor> colors = bike.getBikeColorList();
            for (BikeColor color : colors) {
                bikeColors.add(color.getName());
            }

        return bikeColors;
    }
    public List<String> getBikeImgUrl(Bike bike) {
        List<String> bikeImgUrls = new ArrayList<>();
        List<BikeImage> bikeImages = bike.getBikeImageList();
        for (BikeImage imgUrl: bikeImages
             ) {
            bikeImgUrls.add(imgUrl.getImgUrl());
        }
        return bikeImgUrls;
    }

}
