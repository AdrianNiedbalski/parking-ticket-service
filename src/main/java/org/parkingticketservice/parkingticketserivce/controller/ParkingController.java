package org.parkingticketservice.parkingticketserivce.controller;

import lombok.extern.slf4j.Slf4j;
import org.parkingticketservice.parkingticketserivce.dto.SuccessfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @PostMapping("/number/add")
    public ResponseEntity<SuccessfulResponse> numberAdd(@RequestParam String number) {
        SuccessfulResponse response = new SuccessfulResponse(
                null,
                "Inactive");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/check")
    public ResponseEntity<SuccessfulResponse> numberCheck(@RequestParam String number) {
        SuccessfulResponse response = new SuccessfulResponse(
                null,
                "Inactive");
        return ResponseEntity.ok(response);
    }

}