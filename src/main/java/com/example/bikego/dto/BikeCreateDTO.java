package com.example.bikego.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BikeCreateDTO {
    private String name;
    @Schema(description = "biển số xe",
            example = "62C1-34566"
    )
    private String plateNumber;
    private int rate;
    private int numOfRide;
    @Schema(description = "phân phối của xe",
            example = "125")
    private int bikeDisplacement;
    private BigDecimal price;
    @Schema(description = "hãng xe",
            example = "Honda")
    private String bikeBrandName;
    @Schema(description = "loại xe",
            example = "Xe tay ga")
    private String bikeTypeName;
    private String ownerEmail;
    private String bikeStatusName;
    private List<String> bikeColorName;
    private String imgUrl;

}
