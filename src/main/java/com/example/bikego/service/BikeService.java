package com.example.bikego.service;

import com.example.bikego.dto.*;
import com.example.bikego.exception.InvalidRequestForBike;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BikeService {
    Page<BikeCreateDTO> getFilterPaging(BikePage bikePage, BikeFilterDTO bikeFilterDTO);

    boolean sortValidation(String sortDirection, String sortBy);
    ResponseEntity<ResponseObject> createBike(String uid, BikeCreateDTO bikeCreateDTO);

    ResponseEntity<ResponseObject> getAllBike();

    ResponseEntity<ResponseObject> softDeleteBike(Long id);

    ResponseEntity<ResponseObject> updateBike(Long id, BikeUpdateDTO bikeUpdateDTO);

    ResponseEntity<ResponseObject> filterBikeByStatus(String uid, int pageNumber, int pageSize, Long idStatus);
}
