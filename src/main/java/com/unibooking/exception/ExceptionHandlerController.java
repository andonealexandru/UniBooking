package com.unibooking.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {

    @ExceptionHandler(BuildingNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBuildingNotFoundException(
            BuildingNotFoundException buildingNotFoundException, HttpServletRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(buildingNotFoundException, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRoomNotFoundException(
            RoomNotFoundException roomNotFoundException, HttpServletRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(roomNotFoundException, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePersonNotFoundException(
            PersonNotFoundException personNotFoundException, HttpServletRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(personNotFoundException, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<ExceptionResponse> handleRoomNotAvailableException(
            RoomNotAvailableException roomNotAvailableException, HttpServletRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(roomNotAvailableException, request, HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(StartAfterEndException.class)
    public ResponseEntity<ExceptionResponse> handleStartAfterEndException(
            StartAfterEndException startAfterEndException, HttpServletRequest request
    ) {
        ExceptionResponse response = new ExceptionResponse(startAfterEndException, request, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        StringBuilder strBuilder = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(String.format("%s: %s\n", fieldName, message));
        });

        ExceptionResponse response = new ExceptionResponse(strBuilder.toString(), request, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
