package org.parkingticketservice.parkingticketserivce.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.parkingticketservice.parkingticketserivce.dto.SuccessfulResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParkingService {
    public SuccessfulResponse addNumber(String number) {
        return new SuccessfulResponse(null, "Number added!");
    }

    public SuccessfulResponse checkNumber(String number) {
        return new SuccessfulResponse(null, "Valid!");
    }
}
