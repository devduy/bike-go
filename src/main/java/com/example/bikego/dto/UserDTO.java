package com.example.bikego.dto;

import com.example.bikego.common.Gender;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String createDate;
    private String updateDate;
    private String phone;
    private String birthday;
    private Gender gender;
    private String ownerShopName;
    private String ownerShopAddress;
}
