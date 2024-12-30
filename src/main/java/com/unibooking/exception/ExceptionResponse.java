package com.unibooking.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ExceptionResponse {

    @Setter(AccessLevel.NONE)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String timestamp;
    Integer status;
    String error;
    String path;

    ExceptionResponse(RuntimeException exception, HttpServletRequest request, HttpStatus statusCode) {
        this.timestamp = LocalDateTime.now().format(formatter);
        this.status = statusCode.value();
        this.error = exception.getMessage();
        this.path = request.getRequestURI();
    }

    ExceptionResponse(String exceptionMessage, HttpServletRequest request, HttpStatus statusCode) {
        this.timestamp = LocalDateTime.now().format(formatter);
        this.status = statusCode.value();
        this.error = exceptionMessage;
        this.path = request.getRequestURI();
    }
}
