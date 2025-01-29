package com.unibooking.web;

import com.unibooking.domain.enumeration.Role;
import com.unibooking.service.PersonService;
import com.unibooking.service.dto.PersonResponseDTO;
import com.unibooking.service.dto.PersonWithAccessResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    private ResponseEntity<List<PersonWithAccessResponseDTO>> retrieveAllPeople(@RequestParam(required = false) Optional<Role> type) {
        return ResponseEntity.ok(personService.findAllPeople(type));
    }

    @PostMapping("/{personId}/buildings/{buildingId}")
    private ResponseEntity<Void> assignBuildingToPerson(@PathVariable Long personId, @PathVariable Long buildingId) {
        personService.assignBuildingToPerson(personId, buildingId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{personId}/buildings/{buildingId}")
    private ResponseEntity<Void> removeBuildingFromPerson(@PathVariable Long personId, @PathVariable Long buildingId) {
        personService.removeBuildingFromPerson(personId, buildingId);

        return ResponseEntity.noContent().build();
    }

}
