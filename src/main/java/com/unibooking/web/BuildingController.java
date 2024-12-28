package com.unibooking.web;

import com.unibooking.service.BuildingService;
import com.unibooking.service.dto.BuildingDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/building")
@AllArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    private ResponseEntity<Void> createBuilding(@RequestBody @Valid BuildingDTO buildingDTO) {
        buildingService.createBuilding(buildingDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    private ResponseEntity<Page<BuildingDTO>> getBuildingsPage(Pageable pageable) {
        return ResponseEntity.ok(buildingService.findAllBuildings(pageable));
    }

}
