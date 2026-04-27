package org.parkingticketservice.parkingticketserivce.controller;

import org.parkingticketservice.parkingticketserivce.dto.ParkingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @GetMapping("/add-plate-number")
    public ResponseEntity<ParkingResponse> addPlateNumber2(String plateNumber) {
        ParkingResponse response = new ParkingResponse(
                "PK22222",
                null,
                "Active",
                "Parking ticket is valid"
        );

        return ResponseEntity.ok(response);
    }
}