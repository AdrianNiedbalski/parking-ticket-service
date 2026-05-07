package org.parkingticketservice.parkingticketserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParkingResponse {
    private Instant expiryTime;
    private String status;
}
