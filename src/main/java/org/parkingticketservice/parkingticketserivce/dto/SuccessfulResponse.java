package org.parkingticketservice.parkingticketserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SuccessfulResponse {
    private Instant validUntil;
    private String status;
}
