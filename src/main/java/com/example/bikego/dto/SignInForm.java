package com.example.bikego.dto;

import com.example.bikego.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInForm {
    @NotEmpty(message = "Required field!")
    private String firstName;

    @NotEmpty(message = "Required field!")
    private String lastName;

    @Size(max = 35, message
            = "Email must be less than 35 characters")
    @Email(message = "Need to have an email domain")
    @NotEmpty(message = "Required field!")
    private String email;

    private String password;

    @Size(min = 10, max = 12)
    @NotEmpty(message = "Required field!")
    private String phone;

    @NotEmpty(message = "Required field!")
    @Schema(description = "MALE/FEMALE", example = "MALE")

    private String gender;

    @Past(message = "Date must be from past")
    @Schema(description = "dd/MM/yyyy")
    @NotEmpty(message = "Required field!")
    private String birthday;

    @NotEmpty(message = "Required field!")
    @Schema(description = "Owner shop name")
    private String ownerShopName;

    @NotEmpty(message = "Required field!")
    @Schema(description = "Owner shop name")
    private String ownerShopAddress;


}
