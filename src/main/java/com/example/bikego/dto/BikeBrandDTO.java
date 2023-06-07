package com.example.bikego.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BikeBrandDTO {
    private Long id;
    private String name;
    private String description;
}
