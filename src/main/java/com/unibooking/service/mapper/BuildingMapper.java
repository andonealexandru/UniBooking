package com.unibooking.service.mapper;

import com.unibooking.domain.Building;
import com.unibooking.service.dto.BuildingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    BuildingDTO toDto(Building entity);
    Building toEntity(BuildingDTO dto);
}
