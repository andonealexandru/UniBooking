package com.unibooking.service.dto;

import com.unibooking.validation.ValidTimeFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class BookingDTO {
    @NotNull(message = "Provide a date for the reservation")
    LocalDate date;

    @NotNull
    @ValidTimeFormat
    Integer startTime;

    @NotNull
    @ValidTimeFormat
    Integer endTime;

    @NotNull
    String roomCode;
}
