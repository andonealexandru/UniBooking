package com.unibooking.service;


import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import com.unibooking.exception.RoomNotFoundException;
import com.unibooking.repository.RoomRepository;
import com.unibooking.service.dto.RoomDTO;
import com.unibooking.service.mapper.RoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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

    public Room findRoomByCodeStrict(String code) {
        return roomRepository
                .findByCode(code)
                .orElseThrow(() -> new RoomNotFoundException("Building " + code + " not found."));
    }

    public Page<RoomDTO> findAllRooms(Pageable pageable) {
        return roomRepository
                .findAll(pageable)
                .map(roomMapper::toDto);
    }
}
