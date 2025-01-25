package com.unibooking.service.mapper;

import com.unibooking.domain.Building;
import com.unibooking.service.dto.BuildingDTO;
import com.unibooking.service.dto.BuildingResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", imports = DateTimeFormatter.class)
public interface BuildingMapper {
    BuildingDTO toDto(Building entity);

    @Mapping(target = "start", expression = "java(entity.getStart().format(DateTimeFormatter.ofPattern(\"HH:mm\")))")
    @Mapping(target = "end", expression = "java(entity.getEnd().format(DateTimeFormatter.ofPattern(\"HH:mm\")))")
    BuildingResponseDTO toResponseDto(Building entity);

    Building toEntity(BuildingDTO dto);
}
