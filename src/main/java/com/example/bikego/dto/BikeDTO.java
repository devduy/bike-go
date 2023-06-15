package com.example.bikego.dto;

import com.example.bikego.entity.Bike.BikeBrand;
import com.example.bikego.entity.Bike.BikeColor;
import com.example.bikego.entity.Bike.BikeImage;
import com.example.bikego.entity.Bike.BikeType;
import com.example.bikego.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BikeDTO {
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
    private OwnerShopDTO ownerShopDTO;
    private String bikeStatus;
    private List<String> imgUrl;
    private List<String> colorsName;

}
