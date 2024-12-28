package com.unibooking.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomDTO {

    @NotNull(message = "You must provide a code for the room")
    String code;
    @NotNull(message = "You must provide the capacity of the room")
    Integer capacity;
    @NotNull(message = "You must provide the building for the room")
    String buildingCode;
}
