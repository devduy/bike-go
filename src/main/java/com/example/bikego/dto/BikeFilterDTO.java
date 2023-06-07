package com.example.bikego.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Builder
public class BikeFilterDTO {
    private String running;
    private String giving;
    private String notGive;
}
