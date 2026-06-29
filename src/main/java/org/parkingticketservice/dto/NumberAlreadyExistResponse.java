package org.parkingticketservice.dto;

import lombok.Data;

import java.util.Map;

@Data
public class NumberAlreadyExistResponse {
    private String status;
    private Map<String, String> errors;
}
