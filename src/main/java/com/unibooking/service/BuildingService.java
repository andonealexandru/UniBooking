package com.unibooking.service;

import com.unibooking.domain.Building;
import com.unibooking.exception.BuildingNotFoundException;
import com.unibooking.repository.BuildingRepository;
import com.unibooking.service.dto.BuildingDTO;
import com.unibooking.service.mapper.BuildingMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;

    public void createBuilding(BuildingDTO buildingDTO) {
        Building newBuilding = buildingMapper.toEntity(buildingDTO);
        buildingRepository.save(newBuilding);
    }

    public Building findBuildingByCodeStrict(String code) {
        return buildingRepository
                .findByCode(code)
                .orElseThrow(() -> new BuildingNotFoundException("Building " + code + " not found."));
    }

    public Page<BuildingDTO> findAllBuildings(Pageable pageable) {
        return buildingRepository
                .findAll(pageable)
                .map(buildingMapper::toDto);
    }

    public Boolean isBuildingAvailableForInterval(Building building, LocalTime start, LocalTime end) {
        return start.isAfter(building.getStart().minusSeconds(1)) &&
                end.isBefore(building.getEnd().plusSeconds(1));
    }

}
