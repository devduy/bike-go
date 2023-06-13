package com.example.bikego.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentBikeForm {
    private String uid;
    private Long bikeId;
    @Schema(description = "dd/MM/yyyy",example = "dd/MM/yyyy")
    @NotEmpty(message = "Required field!")
    private String endRentDate;
}
