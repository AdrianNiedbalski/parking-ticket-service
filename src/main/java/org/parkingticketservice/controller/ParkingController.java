package org.parkingticketservice.controller;

import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1")
public class ParkingController {
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/number/add")
    public ResponseEntity<SuccessfulResponse> numberAdd(@RequestParam @Size(min = 5, max = 10) String number) {
        SuccessfulResponse response = parkingService.addNumber(number);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/check")
    public ResponseEntity<SuccessfulResponse> numberCheck(@RequestParam @Size(min = 5, max = 10) String number) {
        SuccessfulResponse response = parkingService.checkNumber(number);
        return ResponseEntity.ok(response);
    }
}