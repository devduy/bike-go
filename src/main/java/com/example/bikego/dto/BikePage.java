package com.example.bikego.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BikePage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortDirection;
    private String sortBy;
}
