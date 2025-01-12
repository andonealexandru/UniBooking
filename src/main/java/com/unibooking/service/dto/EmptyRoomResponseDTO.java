package com.unibooking.service.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

public record EmptyRoomResponseDTO(
        LocalDateTime start,
        LocalDateTime end,
        @JsonUnwrapped RoomResponseDTO roomResponseDTO) {

}
