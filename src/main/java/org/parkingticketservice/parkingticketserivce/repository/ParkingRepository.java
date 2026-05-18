package org.parkingticketservice.parkingticketserivce.repository;

import org.parkingticketservice.parkingticketserivce.entity.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingRecord, Long> {
    Optional<ParkingRecord> findByPlateNumber(String plateNumber);

    boolean existsByPlateNumber(String plateNumber);
}
