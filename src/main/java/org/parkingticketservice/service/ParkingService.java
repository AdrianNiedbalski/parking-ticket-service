package org.parkingticketservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.entity.ParkingRecord;
import org.parkingticketservice.repository.ParkingRepository;
import org.parkingticketservice.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public SuccessfulResponse addNumber(String number) {
        ParkingRecord record = new ParkingRecord();
        record.setPlateNumber(number);

        ParkingRecord savedRecord = parkingRepository.save(record);
        Instant validUntil = TimeUtils.calculateValidUntil(savedRecord.getCreatedAt());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(java.time.ZoneId.systemDefault());
        String formatted = formatter.format(validUntil);

        return new SuccessfulResponse(formatted, "Number added!");
    }

    public SuccessfulResponse checkNumber(String number) {

        if (parkingRepository.existsByPlateNumber(number)) {
            return new SuccessfulResponse(null, "Number exists!");
        } else {
            return new SuccessfulResponse(null, "Number does not exist!");
        }
    }


}
