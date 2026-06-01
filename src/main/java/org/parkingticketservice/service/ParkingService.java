package org.parkingticketservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.entity.ParkingRecord;
import org.parkingticketservice.repository.ParkingRepository;
import org.parkingticketservice.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public SuccessfulResponse addNumber(String number) {
        Optional<ParkingRecord> optFoundRecord = parkingRepository.findByPlateNumber(number);

        if (optFoundRecord.isEmpty()) {
            ParkingRecord record = new ParkingRecord();
            record.setPlateNumber(number);
            return saveAndBuildResponse(record, "Number added!");
        }

        ParkingRecord parkingRecord = optFoundRecord.get();
        Instant createdAt = parkingRecord.getCreatedAt();
        Instant now = Instant.now();
        Duration parkedDuration = Duration.between(createdAt, now);

        // Parking expired but same day — blocked
        LocalDate today = LocalDate.now();
        LocalDate recordDay = createdAt.atZone(ZoneOffset.UTC).toLocalDate();


        // Still within the 2-hour free window
        if (parkedDuration.toMinutes() < 120 && today.isEqual(recordDay)) {
            String expiresAt = formatInstant(createdAt.plus(Duration.ofHours(2)));
            return new SuccessfulResponse(expiresAt, "You still have free parking, it will expire at " + expiresAt);
        }

        if (today.equals(recordDay)) {
            return new SuccessfulResponse(null, "You can add this number once per day, you already added it today!");
        }

        // Different day — reset the record
        parkingRecord.setCreatedAt(now);
        return saveAndBuildResponse(parkingRecord, "Number added!");
    }

    private SuccessfulResponse saveAndBuildResponse(ParkingRecord record, String message) {
        ParkingRecord saved = parkingRepository.save(record);
        String validUntil = formatInstant(TimeUtils.calculateValidUntil(saved.getCreatedAt()));
        return new SuccessfulResponse(validUntil, message);
    }

    private String formatInstant(Instant instant) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(instant);
    }

    public SuccessfulResponse checkNumber(String number) {

        if (parkingRepository.existsByPlateNumber(number)) {
            return new SuccessfulResponse(null, "Number exists!");
        } else {
            return new SuccessfulResponse(null, "Number does not exist!");
        }
    }
}
