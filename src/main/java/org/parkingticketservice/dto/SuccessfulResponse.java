package org.parkingticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SuccessfulResponse {
    private String validUntil;
    private String status;
}
