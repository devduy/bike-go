package com.example.bikego.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerShopDTO {
    private Long id;
    private String name;
    private String address;
    private String imgUrl;
}
