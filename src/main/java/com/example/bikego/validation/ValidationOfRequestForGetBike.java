package com.example.bikego.validation;

import com.example.bikego.exception.InvalidRequestForBike;
import org.springframework.stereotype.Service;

@Service
public class ValidationOfRequestForGetBike {
    public void validateRequestForGetBike(int page, int size) throws InvalidRequestForBike {
        if(page < 1) {
            throw new InvalidRequestForBike("Page number must be greater than 0");
        }
        if(size < 1) {
            throw new InvalidRequestForBike("Page size must be greater than 0");
        }
    }
}
