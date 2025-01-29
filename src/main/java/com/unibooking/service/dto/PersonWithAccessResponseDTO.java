package com.unibooking.service.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PersonWithAccessResponseDTO(@JsonUnwrapped PersonResponseDTO personResponseDTO,
                                          List<BuildingResponseDTO> accessibleBuildings) {

}
