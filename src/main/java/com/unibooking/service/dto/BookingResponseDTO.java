package com.unibooking.service.dto;

import com.unibooking.validation.ValidTimeRange;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
@ValidTimeRange
public class BookingResponseDTO {

    Long id;
    LocalDate date;
    String startTime;
    String endTime;
    String roomCode;
    String status;
}
