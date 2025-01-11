package com.unibooking.service.mapper;

import com.unibooking.domain.Building;
import com.unibooking.service.dto.BuildingDTO;
import com.unibooking.service.dto.BuildingResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    BuildingDTO toDto(Building entity);
    BuildingResponseDTO toResponseDto(Building entity);
    Building toEntity(BuildingDTO dto);
}
