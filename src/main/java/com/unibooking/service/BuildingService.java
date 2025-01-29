package com.unibooking.service;

import com.unibooking.domain.Building;
import com.unibooking.domain.Person;
import com.unibooking.exception.BuildingNotFoundException;
import com.unibooking.repository.BuildingRepository;
import com.unibooking.repository.PersonBuildingRepository;
import com.unibooking.service.dto.BuildingDTO;
import com.unibooking.service.dto.BuildingResponseDTO;
import com.unibooking.service.mapper.BuildingMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingService {

    private final AuthenticationService authenticationService;
    private final BuildingRepository buildingRepository;
    private final PersonBuildingRepository personBuildingRepository;
    private final BuildingMapper buildingMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    public void createBuilding(BuildingDTO buildingDTO) {
        Building newBuilding = buildingMapper.toEntity(buildingDTO);
        newBuilding.setIsActive(true);
        buildingRepository.save(newBuilding);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateBuilding(Long id, BuildingDTO buildingDTO) {
        Building building = findBuildingByIdStrict(id);

        building.setAddress(buildingDTO.getAddress());
        building.setStart(buildingDTO.getStart());
        building.setEnd(buildingDTO.getEnd());

        buildingRepository.save(building);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteBuilding(Long id) {
        Building building = findBuildingByIdStrict(id);
        building.setIsActive(false);
        buildingRepository.save(building);
    }

    public Building findBuildingByCodeStrict(String code) {
        return buildingRepository
                .findByCode(code)
                .orElseThrow(() -> new BuildingNotFoundException("Building " + code + " not found."));
    }

    public Building findBuildingByIdStrict(Long id) {
        return buildingRepository
                .findById(id)
                .orElseThrow(() -> new BuildingNotFoundException("Building " + id + " not found."));
    }

    public Page<BuildingResponseDTO> findAllBuildings(Pageable pageable) {
        return buildingRepository
                .findAll(pageable)
                .map(buildingMapper::toResponseDto);
    }

    public List<BuildingResponseDTO> findAllBuildings() {
        return buildingRepository
                .findAllByOrderByCode()
                .stream().map(buildingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BuildingResponseDTO> findAllBuildingsForMe() {
        Person person = authenticationService.getCurrentUser();
        return findAllBuildingsForPerson(person);
    }

    public List<BuildingResponseDTO> findAllBuildingsForPerson(Person person) {
        return personBuildingRepository.findAllByPersonBuildingId_PersonOrderByPersonBuildingId_Building_Code(person)
                .stream()
                .map(pb -> buildingMapper.toResponseDto(pb.getPersonBuildingId().getBuilding()))
                .collect(Collectors.toList());
    }

    public Boolean isBuildingAvailableForInterval(Building building, LocalTime start, LocalTime end) {
        return start.isAfter(building.getStart().minusSeconds(1)) &&
                end.isBefore(building.getEnd().plusSeconds(1));
    }

}
