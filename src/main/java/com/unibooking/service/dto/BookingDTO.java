package com.unibooking.service.dto;

import com.unibooking.validation.ValidTimeFormat;
import com.unibooking.validation.ValidTimeRange;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
@ValidTimeRange
public class BookingDTO {
    @NotNull(message = "Provide a date for the reservation")
    LocalDate date;

    @NotNull
    LocalTime startTime;

    @NotNull
    LocalTime endTime;

    @NotNull
    String roomCode;
}
