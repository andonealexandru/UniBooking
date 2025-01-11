package com.unibooking.service.mapper;

import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import com.unibooking.service.dto.RoomDTO;
import com.unibooking.service.dto.RoomResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "buildingCode", source = "building.code")
    RoomDTO toDto(Room entity);

    RoomResponseDTO toResponseDto(Room entity);

    Room toEntity(RoomDTO dto);
}
