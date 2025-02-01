package com.unibooking.web;

import com.unibooking.domain.enumeration.Role;
import com.unibooking.service.PersonService;
import com.unibooking.service.dto.PersonResponseDTO;
import com.unibooking.service.dto.PersonUpdateRequestDTO;
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

    @PatchMapping("/{id}")
    private ResponseEntity<Void> updatePersonData(@PathVariable Long id, @RequestBody PersonUpdateRequestDTO person) {
        personService.updatePerson(id, person);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    private ResponseEntity<List<PersonWithAccessResponseDTO>> retrieveAllPeople(
            @RequestParam(required = false) Role type,
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(personService.findAllPeople(type, query));
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
