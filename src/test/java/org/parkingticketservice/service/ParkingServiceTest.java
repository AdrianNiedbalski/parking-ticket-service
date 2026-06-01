package org.parkingticketservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.entity.ParkingRecord;
import org.parkingticketservice.repository.ParkingRepository;
import org.parkingticketservice.utils.TimeUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("ParkingService Unit Tests")
class ParkingServiceTest {

    @Mock
    private ParkingRepository parkingRepository;

    @InjectMocks
    private ParkingService parkingService;

    private ParkingRecord testRecord;
    private String testPlateNumber;
    private Instant testInstant;

    @BeforeEach
    void setUp() {
        testPlateNumber = "ABC123";
        testInstant = Instant.now();

        testRecord = new ParkingRecord();
        testRecord.setId(1L);
        testRecord.setPlateNumber(testPlateNumber);
        testRecord.setCreatedAt(testInstant);
    }

    // ==================== addNumber() Tests ====================

    @Test
    @DisplayName("addNumber should successfully add a new plate number")
    void testAddNumberSuccess() {
        // Arrange
        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(testRecord);

        // Act
        SuccessfulResponse response = parkingService.addNumber(testPlateNumber);

        // Assert
        assertNotNull(response);
        assertEquals("Number added!", response.getStatus());
        assertNotNull(response.getValidUntil());
        verify(parkingRepository, times(1)).save(any(ParkingRecord.class));
    }

    @Test
    @DisplayName("addNumber should set the correct plate number on the record")
    void testAddNumberSetsCorrectPlateNumber() {
        // Arrange
        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(testRecord);

        // Act
        parkingService.addNumber(testPlateNumber);

        // Assert
        verify(parkingRepository).save(argThat(parkingRecord ->
                parkingRecord.getPlateNumber().equals(testPlateNumber)
        ));
    }

    @Test
    @DisplayName("addNumber should calculate valid until time correctly (2 hours from creation)")
    void testAddNumberCalculatesValidUntilCorrectly() {
        // Arrange
        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(testRecord);
        Instant expectedValidUntil = TimeUtils.calculateValidUntil(testInstant);

        // Act
        SuccessfulResponse response = parkingService.addNumber(testPlateNumber);

        // Assert
        assertNotNull(response.getValidUntil());
        String responseData = response.getValidUntil();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(java.time.ZoneId.systemDefault());
        String expectedFormatted = formatter.format(expectedValidUntil);

        assertEquals(expectedFormatted, responseData);
    }

    @Test
    @DisplayName("addNumber should call repository save exactly once")
    void testAddNumberCallsRepositorySaveOnce() {
        // Arrange
        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(testRecord);

        // Act
        parkingService.addNumber(testPlateNumber);

        // Assert
        verify(parkingRepository, times(1)).save(any(ParkingRecord.class));
        verifyNoMoreInteractions(parkingRepository);
    }

    @Test
    @DisplayName("addNumber should return response with non-null data")
    void testAddNumberReturnsDataNotNull() {
        // Arrange
        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(testRecord);

        // Act
        SuccessfulResponse response = parkingService.addNumber(testPlateNumber);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getValidUntil());
        assertInstanceOf(String.class, response.getValidUntil());
    }

    @Test
    @DisplayName("addNumber should handle different plate numbers")
    void testAddNumberHandlesDifferentPlateNumbers() {
        // Arrange
        String[] plateNumbers = {"XYZ789", "DEF456", "GHI135"};

        for (String plate : plateNumbers) {
            ParkingRecord parkingRecord = new ParkingRecord();
            parkingRecord.setPlateNumber(plate);
            parkingRecord.setCreatedAt(testInstant);

            when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(parkingRecord);

            // Act
            SuccessfulResponse response = parkingService.addNumber(plate);

            // Assert
            assertNotNull(response);
            assertEquals("Number added!", response.getStatus());
        }
    }

    // ==================== checkNumber() Tests ====================

    @Test
    @DisplayName("checkNumber should return 'Number exists!' when plate number exists")
    void testCheckNumberExists() {
        // Arrange
        when(parkingRepository.existsByPlateNumber(testPlateNumber)).thenReturn(true);

        // Act
        SuccessfulResponse response = parkingService.checkNumber(testPlateNumber);

        // Assert
        assertNotNull(response);
        assertEquals("Number exists!", response.getStatus());
        assertNull(response.getValidUntil());
    }

    @Test
    @DisplayName("checkNumber should return 'Number does not exist!' when plate number does not exist")
    void testCheckNumberDoesNotExist() {
        // Arrange
        when(parkingRepository.existsByPlateNumber(testPlateNumber)).thenReturn(false);

        // Act
        SuccessfulResponse response = parkingService.checkNumber(testPlateNumber);

        // Assert
        assertNotNull(response);
        assertEquals("Number does not exist!", response.getStatus());
        assertNull(response.getValidUntil());
    }

    @Test
    @DisplayName("checkNumber should call repository existsByPlateNumber once")
    void testCheckNumberCallsRepositoryOnce() {
        // Arrange
        when(parkingRepository.existsByPlateNumber(testPlateNumber)).thenReturn(true);

        // Act
        parkingService.checkNumber(testPlateNumber);

        // Assert
        verify(parkingRepository, times(1)).existsByPlateNumber(testPlateNumber);
        verifyNoMoreInteractions(parkingRepository);
    }

    @Test
    @DisplayName("checkNumber should return correct response with null data")
    void testCheckNumberReturnsNullData() {
        // Arrange
        when(parkingRepository.existsByPlateNumber(testPlateNumber)).thenReturn(true);

        // Act
        SuccessfulResponse response = parkingService.checkNumber(testPlateNumber);

        // Assert
        assertNotNull(response);
        assertNull(response.getValidUntil());
    }

    @Test
    @DisplayName("checkNumber should handle multiple plate numbers correctly")
    void testCheckNumberHandlesMultiplePlateNumbers() {
        // Arrange
        String existingPlate = "ABC123";
        String nonExistingPlate = "XYZ999";

        when(parkingRepository.existsByPlateNumber(existingPlate)).thenReturn(true);
        when(parkingRepository.existsByPlateNumber(nonExistingPlate)).thenReturn(false);

        // Act
        SuccessfulResponse responseExists = parkingService.checkNumber(existingPlate);
        SuccessfulResponse responseNotExists = parkingService.checkNumber(nonExistingPlate);

        // Assert
        assertEquals("Number exists!", responseExists.getStatus());
        assertEquals("Number does not exist!", responseNotExists.getStatus());
    }

    @Test
    @DisplayName("checkNumber should pass the correct plate number to repository")
    void testCheckNumberPassesCorrectPlateNumber() {
        // Arrange
        String specificPlate = "TEST001";
        when(parkingRepository.existsByPlateNumber(specificPlate)).thenReturn(true);

        // Act
        parkingService.checkNumber(specificPlate);

        // Assert
        verify(parkingRepository).existsByPlateNumber(specificPlate);
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("addNumber should handle special characters in plate number")
    void testAddNumberWithSpecialCharacters() {
        // Arrange
        String specialPlate = "AB-123-XY";
        ParkingRecord specialRecord = new ParkingRecord();
        specialRecord.setPlateNumber(specialPlate);
        specialRecord.setCreatedAt(testInstant);

        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(specialRecord);

        // Act
        SuccessfulResponse response = parkingService.addNumber(specialPlate);

        // Assert
        assertNotNull(response);
        assertEquals("Number added!", response.getStatus());
    }

    @Test
    @DisplayName("checkNumber should handle special characters in plate number")
    void testCheckNumberWithSpecialCharacters() {
        // Arrange
        String specialPlate = "AB-123-XY";
        when(parkingRepository.existsByPlateNumber(specialPlate)).thenReturn(true);

        // Act
        SuccessfulResponse response = parkingService.checkNumber(specialPlate);

        // Assert
        assertEquals("Number exists!", response.getStatus());
    }

    @Test
    @DisplayName("addNumber should handle empty string")
    void testAddNumberWithEmptyString() {
        // Arrange
        String emptyPlate = "";
        ParkingRecord emptyRecord = new ParkingRecord();
        emptyRecord.setPlateNumber(emptyPlate);
        emptyRecord.setCreatedAt(testInstant);

        when(parkingRepository.save(any(ParkingRecord.class))).thenReturn(emptyRecord);

        // Act
        SuccessfulResponse response = parkingService.addNumber(emptyPlate);

        // Assert
        assertNotNull(response);
        assertEquals("Number added!", response.getStatus());
    }
}