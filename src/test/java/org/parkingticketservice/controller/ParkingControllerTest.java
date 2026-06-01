package org.parkingticketservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.parkingticketservice.constant.ResponseStatus;
import org.parkingticketservice.dto.SuccessfulResponse;
import org.parkingticketservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ParkingController.class)
class ParkingControllerTest {
    //private static String stillValidParkingMessage;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParkingService parkingService;

    @BeforeAll
    static void setUp() {
        //stillValidParkingMessage = ResponseStatus.STILL_VALID_PARKING_MESSAGE;
    }

    @Test
    void addNumber_validPlate_returns200WithResponse() throws Exception {
        SuccessfulResponse response = new SuccessfulResponse("2026-06-01 12:00:00", "Number added!");
        when(parkingService.addNumber("ABC123")).thenReturn(response);

        mockMvc.perform(post("/api/v1/number/add")
                        .param("number", "ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validUntil").value("2026-06-01 12:00:00"))
                .andExpect(jsonPath("$.status").value("Number added!"));

        verify(parkingService).addNumber("ABC123");
    }

    @Test
    void addNumber_caseStillValidFreeParking() throws Exception {
        String stillValidParkingMsg = String.format(ResponseStatus.STILL_VALID_PARKING_MESSAGE, "2026-01-01 14:00:00");

        SuccessfulResponse response = new SuccessfulResponse("2026-01-01 14:00:00", stillValidParkingMsg);
        when(parkingService.addNumber("EBE12345")).thenReturn(response);

        mockMvc.perform(post("/api/v1/number/add")
                        .param("number", "EBE12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validUntil").value("2026-01-01 14:00:00"))
                .andExpect(jsonPath("$.status").value(stillValidParkingMsg));

        verify(parkingService).addNumber("EBE12345");
    }

    @Test
    void addNumber_plateAtMinLength_returns200() throws Exception {
        SuccessfulResponse response = new SuccessfulResponse("2026-06-01 12:00:00", "Number added!");
        when(parkingService.addNumber("AB123")).thenReturn(response);

        mockMvc.perform(post("/api/v1/number/add")
                        .param("number", "AB123"))
                .andExpect(status().isOk());
    }

    @Test
    void addNumber_plateAtMaxLength_returns200() throws Exception {
        SuccessfulResponse response = new SuccessfulResponse("2026-06-01 12:00:00", "Number added!");
        when(parkingService.addNumber("ABC1234567")).thenReturn(response);

        mockMvc.perform(post("/api/v1/number/add")
                        .param("number", "ABC1234567"))
                .andExpect(status().isOk());
    }

    @Test
    void addNumber_plateTooShort_returns400() throws Exception {
        mockMvc.perform(post("/api/v1/number/add")
                        .param("number", "AB12"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(parkingService);
    }

    @Test
    void addNumber_plateTooLong_returns400() throws Exception {
        mockMvc.perform(post("/api/v1/number/add")
                        .param("number", "ABC12345678"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(parkingService);
    }

    @Test
    void addNumber_missingParam_returns400() throws Exception {
        mockMvc.perform(post("/api/v1/number/add"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(parkingService);
    }

    // --- GET /api/v1/number/check ---

    @Test
    void checkNumber_existingPlate_returns200WithExistsMessage() throws Exception {
        SuccessfulResponse response = new SuccessfulResponse(null, "Number exists!");
        when(parkingService.checkNumber("ABC123")).thenReturn(response);

        mockMvc.perform(get("/api/v1/number/check")
                        .param("number", "ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Number exists!"))
                .andExpect(jsonPath("$.validUntil").doesNotExist());

        verify(parkingService).checkNumber("ABC123");
    }

    @Test
    void checkNumber_nonExistingPlate_returns200WithNotExistsMessage() throws Exception {
        SuccessfulResponse response = new SuccessfulResponse(null, "Number does not exist!");
        when(parkingService.checkNumber("XYZ99")).thenReturn(response);

        mockMvc.perform(get("/api/v1/number/check")
                        .param("number", "XYZ99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Number does not exist!"));

        verify(parkingService).checkNumber("XYZ99");
    }

    @Test
    void checkNumber_plateTooShort_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/number/check")
                        .param("number", "AB12"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(parkingService);
    }

    @Test
    void checkNumber_plateTooLong_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/number/check")
                        .param("number", "ABC12345678"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(parkingService);
    }

    @Test
    void checkNumber_missingParam_returns400() throws Exception {
        mockMvc.perform(get("/api/v1/number/check"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(parkingService);
    }
}