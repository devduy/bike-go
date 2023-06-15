package com.example.bikego.service.impl;

import com.example.bikego.common.RentStatus;
import com.example.bikego.dto.*;
import com.example.bikego.entity.Bike.Bike;
import com.example.bikego.entity.Bike.BikeStatus;
import com.example.bikego.entity.RentHistory;
import com.example.bikego.entity.User;
import com.example.bikego.repository.BikeRepository;
import com.example.bikego.repository.BikeStatusRepository;
import com.example.bikego.repository.RentHistoryRepository;
import com.example.bikego.repository.UserRepository;
import com.example.bikego.service.MobileAppService;
import com.example.bikego.utils.DateTimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class MobileAppImpl implements MobileAppService {
    private final BikeRepository bikeRepository;
    private final ModelMapper modelMapper;
    private final BikeStatusRepository bikeStatusRepository;
    private final UserRepository userRepository;
    private final RentHistoryRepository rentHistoryRepository;
    private final BikeServiceImpl bikeService;
    private final UserServiceImpl userService;

    public MobileAppImpl(BikeRepository bikeRepository, ModelMapper modelMapper, BikeStatusRepository bikeStatusRepository, UserRepository userRepository, RentHistoryRepository rentHistoryRepository, BikeServiceImpl bikeService, UserServiceImpl userService) {
        this.bikeRepository = bikeRepository;
        this.modelMapper = modelMapper;
        this.bikeStatusRepository = bikeStatusRepository;
        this.userRepository = userRepository;
        this.rentHistoryRepository = rentHistoryRepository;
        this.bikeService = bikeService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBikeAvailable(int pageNumber, int pageSize) {
        try {
            List<Bike> bikeList = new ArrayList<>();
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Bike> bikePage;
            BikeStatus bikeStatus = bikeStatusRepository.findById(Long.valueOf(4))
                    .orElseThrow(() -> new ValidationException("Bike Status is not existed"));
            bikePage = bikeRepository.findBikeAvailable(bikeStatus, pageable);
            bikeList = bikePage.getContent();
            List<BikeDTO> bikeDTOList = bikeList.stream().map(bikeService::convertToDTO).toList();
            Pagination pagination = new Pagination(bikePage.getNumber(), bikePage.getTotalElements(), bikePage.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Get success", pagination, bikeDTOList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));
        }

    }

    @Override
    public ResponseEntity<ResponseObject> rentBike(RentBikeForm rentBikeForm) {
        try {
            User user = userRepository.findById(rentBikeForm.getUid()).orElseThrow(() -> new ValidationException("User is not existed"));
            Bike bike = bikeRepository.findById(rentBikeForm.getBikeId()).orElseThrow(() -> new ValidationException("Bike is not existed"));
            BikeStatus bikeStatus = bikeStatusRepository.findById(1L).orElseThrow(() -> new ValidationException("Bike Status is not existed"));
            if(bike.getBikeStatus() == bikeStatus) {
                throw new Exception("Bike is not available");
            }
            bike.setBikeStatus(bikeStatus);
            bike.setRentUser(user);
            RentHistory rentHistory = new RentHistory();
            rentHistory.setRentUser(user);
            rentHistory.setBikeRent(bike);
            rentHistory.setStartRentDate(DateTimeUtils.convertStringToLocalDate(DateTimeUtils.dateNow()));
            rentHistory.setEndRentDate(DateTimeUtils.convertStringToLocalDate(rentBikeForm.getEndRentDate()));
            rentHistory.setRentStatus(RentStatus.IN_PROGRESS);
            rentHistory.setOwner(bike.getUser());
            bikeRepository.save(bike);
            rentHistoryRepository.save(rentHistory);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Rent success", null, null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));

        }

    }

    @Override
    public ResponseEntity<ResponseObject> finish(String uid, Long bikeId) {
        try {

            User user = userRepository.findById(uid).orElseThrow(() -> new ValidationException("User is not existed"));
            Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new ValidationException("Bike is not existed"));
            BikeStatus bikeStatus = bikeStatusRepository.findById(1L).orElseThrow(() -> new ValidationException("Bike Status is not existed"));
            if(bike.getBikeStatus() == bikeStatus) {
                bike.setBikeStatus(bikeStatusRepository.findById(4L)
                        .orElseThrow(() -> new ValidationException("Bike Status is not existed")));
                bike.setRentUser(null);

                RentHistory rentHistory = rentHistoryRepository.findByRentUserAndRentStatusAndBikeRent(user,RentStatus.IN_PROGRESS,bike);
                rentHistory.setTotalPrice(totalPrice(bike.getPrice(),rentHistory.getStartRentDate(),rentHistory.getEndRentDate()));
                rentHistory.setRentStatus(RentStatus.COMPLETED);
                bike.setNumOfRide(bike.getNumOfRide()+1);
                bikeRepository.save(bike);
                rentHistoryRepository.save(rentHistory);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(HttpStatus.OK.toString(), "Success", null, null));
            }
            throw new Exception("Bike Status is not valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));

        }
    }

    @Override
    public ResponseEntity<ResponseObject> getRentHistory(String uid,int pageNumber, int pageSize, String status) {
        try {
            User user = userRepository.findById(uid).orElseThrow(() -> new ValidationException("User is not existed"));
            List<RentHistory> rentHistories = new ArrayList<>();
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<RentHistory> bikePage;
            if(status == null) {
                bikePage = rentHistoryRepository.findAllByRentUser(user,pageable);
            }else {
                bikePage = rentHistoryRepository.findByUserAndRentStatusPage(user,RentStatus.valueOf(status),pageable);


            }
            rentHistories = bikePage.getContent();
            List<RentHistoryDTO> rentHistoryDTOS = rentHistories.stream().map(this::convertToDTO).toList();
            Pagination pagination = new Pagination(bikePage.getNumber(),bikePage.getTotalElements(),bikePage.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Get success", pagination, rentHistoryDTOS));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null, null));

        }

    }

    public RentHistoryDTO convertToDTO(RentHistory rentHistory) {
        RentHistoryDTO rentHistoryDTO = modelMapper.map(rentHistory, RentHistoryDTO.class);
        rentHistoryDTO.setUserRentDTO(userService.convertToUserRentDTO(rentHistory.getRentUser()));
        rentHistoryDTO.setBikeDTO(bikeService.convertToDTO(rentHistory.getBikeRent()));
        rentHistoryDTO.setOwner(userService.convertToDTO(rentHistory.getOwner()));
        return rentHistoryDTO;
    }

    public BigDecimal totalPrice (BigDecimal price, LocalDate start, LocalDate end) {
        BigDecimal totalPrice;
        long soNgay = ChronoUnit.DAYS.between(start, end);
        totalPrice = new BigDecimal(soNgay).multiply(price);
        return totalPrice;
    }


}
