package org.parkingticketservice.parkingticketserivce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingErrorResponse {
    private String status;
    private Map<String, String> errors;

    public ParkingErrorResponse() {
        this.errors = new HashMap<>();
    }
}
