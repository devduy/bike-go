package com.example.bikego.dto;

import com.example.bikego.common.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserRentDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String birthday;
    private Gender gender;
}
