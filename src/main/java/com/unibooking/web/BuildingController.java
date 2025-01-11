package com.unibooking.web;

import com.unibooking.service.BuildingService;
import com.unibooking.service.RoomService;
import com.unibooking.service.dto.BuildingDTO;
import com.unibooking.service.dto.BuildingResponseDTO;
import com.unibooking.service.dto.RoomResponseDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@AllArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;
    private final RoomService roomService;

    @PostMapping
    private ResponseEntity<Void> createBuilding(@RequestBody @Valid BuildingDTO buildingDTO) {
        buildingService.createBuilding(buildingDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    private ResponseEntity<List<BuildingResponseDTO>> getBuildings() {
        return ResponseEntity.ok(buildingService.findAllBuildings());
    }

    @GetMapping("/{id}/rooms")
    private ResponseEntity<List<RoomResponseDTO>> getBuildingRooms(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findAllRoomsForBuilding(id));
    }

    @GetMapping("/paged")
    private ResponseEntity<Page<BuildingResponseDTO>> getBuildingsPage(Pageable pageable) {
        return ResponseEntity.ok(buildingService.findAllBuildings(pageable));
    }

}
