package com.unibooking.service;


import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import com.unibooking.exception.RoomNotFoundException;
import com.unibooking.repository.RoomRepository;
import com.unibooking.service.dto.RoomDTO;
import com.unibooking.service.dto.RoomResponseDTO;
import com.unibooking.service.mapper.RoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final BuildingService buildingService;

    public void createRoom(RoomDTO roomDTO) {
        Building building = buildingService.findBuildingByCodeStrict(roomDTO.getBuildingCode());

        Room newRoom = roomMapper.toEntity(roomDTO);
        newRoom.setBuilding(building);

        roomRepository.save(newRoom);
    }

    public void deleteAllRoomsInBuilding(Long id) {
        Building building = buildingService.findBuildingByIdStrict(id);
        List<Room> rooms = roomRepository.findAllByBuilding(building);

        rooms.forEach(room -> room.setIsActive(false));

        roomRepository.saveAll(rooms);
    }

    public Room findRoomByCodeStrict(String code) {
        return findRoomByCode(code)
                .orElseThrow(() -> new RoomNotFoundException("Room " + code + " not found."));
    }

    public Optional<Room> findRoomByCode(String code) {
        return roomRepository.findByCode(code);
    }

    public Room findRoomByIdStrict(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room " + id + " not found."));
    }

    public List<RoomResponseDTO> findAllRoomsForBuilding(Long id) {
        Building building = buildingService.findBuildingByIdStrict(id);

        return roomRepository
                .findAllByBuilding(building)
                .stream().map(roomMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Page<RoomDTO> findAllRooms(Pageable pageable) {
        return roomRepository
                .findAll(pageable)
                .map(roomMapper::toDto);
    }
}
