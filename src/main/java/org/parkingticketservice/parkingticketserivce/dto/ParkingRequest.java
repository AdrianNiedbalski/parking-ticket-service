package org.parkingticketservice.parkingticketserivce.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class ParkingRequest {
    @NotBlank
    @Length(min = 5, max = 10)
    private String number;

}
