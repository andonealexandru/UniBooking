package com.unibooking.service.dto;

import com.unibooking.domain.enumeration.WorkstationType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record EmptyRoomRequestDTO(
        String buildingCode,
        String roomCode,
        WorkstationType workstationType,
        Integer capacity,
        Integer workstationCount,
        @NotNull LocalDate date,
        @NotNull LocalTime start,
        @NotNull LocalTime end) {

}
