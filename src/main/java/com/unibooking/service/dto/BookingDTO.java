package com.unibooking.service.dto;

import com.unibooking.validation.ValidTimeFormat;
import com.unibooking.validation.ValidTimeRange;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@ValidTimeRange
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
