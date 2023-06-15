package com.example.bikego.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BikeOwnerDTO {
    private Long id;
    private String name;
    private String plateNumber;
    private int rate;
    private int numOfRide;
    private int bikeDisplacement;
    private BigDecimal price;
    private String bikeBrandName;
    private String bikeTypeName;
    private String createDate;
    private String updateDate;
    private String createdBy;
    private String ownerName;
    private String ownerShop;
    private String ownerShopAddress;
    private String bikeStatus;
    private UserRentDTO userRentDTO;
    private List<String> imgUrl;
    private List<String> colorsName;
}
