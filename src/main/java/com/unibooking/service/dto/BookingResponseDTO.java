package com.unibooking.service.dto;

import com.unibooking.validation.ValidTimeRange;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@ValidTimeRange
public class BookingResponseDTO {

    Long id;
    LocalDate date;
    String startTime;
    String endTime;
    String buildingCode;
    String roomCode;
    String status;
}
