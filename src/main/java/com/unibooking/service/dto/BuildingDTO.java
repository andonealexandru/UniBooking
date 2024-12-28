package com.unibooking.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BuildingDTO {

    @NotNull(message = "You must provide a code for the building")
    String code;

    @NotNull(message = "You must provide an address for the building")
    String address;
}
