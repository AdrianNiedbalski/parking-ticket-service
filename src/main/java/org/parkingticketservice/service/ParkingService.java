package org.parkingticketservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.entity.ParkingRecord;
import org.parkingticketservice.repository.ParkingRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public SuccessfulResponse addNumber(String number) {
        ParkingRecord record = new ParkingRecord();
        record.setPlateNumber(number);

        parkingRepository.save(record);
        return new SuccessfulResponse(null, "Number added!");
    }

    public SuccessfulResponse checkNumber(String number) {

        if (parkingRepository.existsByPlateNumber(number)) {
            return new SuccessfulResponse(null, "Number exists!");
        } else {
            return new SuccessfulResponse(null, "Number does not exist!");
        }
    }
}
