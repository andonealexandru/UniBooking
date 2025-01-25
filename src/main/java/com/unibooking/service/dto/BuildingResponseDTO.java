package com.unibooking.service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;

@Value
@Builder
public class BuildingResponseDTO {
    Long id;
    String code;
    String address;
    String start;
    String end;
}
