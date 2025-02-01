package com.unibooking.service.dto;

import lombok.Value;

import java.util.List;

public record PersonUpdateRequestDTO(String firstName,
                                     String lastName,
                                     String email,
                                     String role,
                                     String code,
                                     List<BuildingResponseDTO> accessibleBuildings) {
}
