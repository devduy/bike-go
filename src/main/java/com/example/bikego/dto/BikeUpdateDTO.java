package com.example.bikego.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BikeUpdateDTO {
    private String name;
    private int rate;
    private int numOfRide;
    private int bikeDisplacement;
    private BigDecimal price;
    private List<String> bikeColorName;
}
