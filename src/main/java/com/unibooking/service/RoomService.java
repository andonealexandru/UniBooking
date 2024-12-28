package com.unibooking.service;


import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import com.unibooking.exception.BuildingNotFoundException;
import com.unibooking.repository.BuildingRepository;
import com.unibooking.repository.RoomRepository;
import com.unibooking.service.dto.RoomDTO;
import com.unibooking.service.mapper.RoomMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BuildingRepository buildingRepository;
    private final RoomMapper roomMapper;

    @Transactional
    public void createRoom(RoomDTO roomDTO) {
        Building building = buildingRepository
                .findByCode(roomDTO.getBuildingCode())
                .orElseThrow(() -> new BuildingNotFoundException("Building " + roomDTO.getBuildingCode() + " not found."));

        Room newRoom = roomMapper.toEntity(roomDTO);
        newRoom.setBuilding(building);

        roomRepository.save(newRoom);
    }

    public Page<RoomDTO> findAllRooms(Pageable pageable) {
        return roomRepository
                .findAll(pageable)
                .map(roomMapper::toDto);
    }
}
