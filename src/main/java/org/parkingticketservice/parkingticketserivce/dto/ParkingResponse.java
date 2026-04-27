package org.parkingticketservice.parkingticketserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParkingResponse {
    private String plateNumber;
    private LocalDateTime expiryTime;
    private String status;
    private String message;
}
