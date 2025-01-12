package com.unibooking.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomResponseDTO {
    Long id;
    String code;
    String buildingCode;
    Integer capacity;
    String workstationType;
    Integer workstationCount;
}
