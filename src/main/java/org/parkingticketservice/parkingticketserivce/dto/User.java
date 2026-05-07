package org.parkingticketservice.parkingticketserivce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class User {
    @NotBlank
    @NotNull
    private String login;

    @NotBlank
    @Length(min = 8)
    private String password;
}
