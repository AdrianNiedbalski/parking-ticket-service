package org.parkingticketservice.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ScannerController {
    private final ParkingService parkingService;

    public ScannerController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }
    /*
    @GetMapping("/number/scan")
    public ResponseEntity<SuccessfulResponse> numberCheck(
            @RequestParam @Size(min = 5, max = 10) String number,
            @RequestHeader("X-Auth-Token") String token) {

        if (!token.equals("super-secret-123")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SuccessfulResponse response = parkingService.checkNumber(number);
        return ResponseEntity.ok(response);
    }
     */
    @GetMapping("/number/scan")
    public ResponseEntity<SuccessfulResponse> numberScan(@RequestParam String number) {
        SuccessfulResponse response = parkingService.checkNumber(number);
        return ResponseEntity.ok(response);
    }
}
