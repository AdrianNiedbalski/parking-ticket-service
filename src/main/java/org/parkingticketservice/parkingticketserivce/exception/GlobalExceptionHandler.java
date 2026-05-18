package org.parkingticketservice.parkingticketserivce.exception;

import jakarta.validation.ConstraintViolationException;
import org.parkingticketservice.parkingticketserivce.dto.ParkingErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ParkingErrorResponse> handleValidExceptions(ConstraintViolationException ex) {
        ParkingErrorResponse errorResponse = new ParkingErrorResponse();
        Map<String, String> errors = errorResponse.getErrors();

        ex.getConstraintViolations()
                .forEach(violation ->
                        errors.put(violation.getPropertyPath().toString(), violation.getMessage())
                );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ParkingErrorResponse> handleIntegrityViolationExceptions(DataIntegrityViolationException ex) {
        ParkingErrorResponse errorResponse = new ParkingErrorResponse();
        errorResponse.setStatus("error");
        errorResponse.getErrors().put("database", "Plate number already exists or violates database constraints.");
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
