package com.unibooking.service;

import com.unibooking.domain.Building;
import com.unibooking.domain.Person;
import com.unibooking.domain.PersonBuilding;
import com.unibooking.domain.PersonBuildingId;
import com.unibooking.domain.enumeration.Role;
import com.unibooking.exception.PersonNotFoundException;
import com.unibooking.repository.PersonBuildingRepository;
import com.unibooking.repository.PersonRepository;
import com.unibooking.service.dto.*;
import com.unibooking.service.mapper.BuildingMapper;
import com.unibooking.service.mapper.PersonMapper;
import com.unibooking.service.specification.PersonSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PersonService {

    private BuildingService buildingService;

    private PersonRepository personRepository;
    private PersonBuildingRepository personBuildingRepository;

    private PasswordEncoder passwordEncoder;
    private PersonMapper personMapper;
    private BuildingMapper buildingMapper;

    public Optional<Person> findPersonByCode(String code) {
        return personRepository.findByCode(code);
    }

    public Person findPersonByCodeStrict(String code) {
        return findPersonByCode(code)
                .orElseThrow(() -> new PersonNotFoundException("No person with code " + code + " found!"));
    }

    public Optional<Person> findPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person findPersonByIdStrict(Long id) {
        return findPersonById(id)
                .orElseThrow(() -> new PersonNotFoundException("No person with id " + id + " found!"));
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException("No person with email " + email + " found!"));
    }

    public void createPerson(PersonDTO personDTO) {
        Person person = personMapper.toEntity(personDTO);

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.STUDENT);

        personRepository.save(person);
    }

    public void updatePerson(Long id, PersonUpdateRequestDTO personDTO) {
        Person person = findPersonByIdStrict(id);

        person.setRole(Role.valueOf(personDTO.role()));
        person.setCode(personDTO.code());
        person.setEmail(personDTO.email());
        person.setFirstName(personDTO.firstName());
        person.setLastName(personDTO.lastName());

        personRepository.save(person);

        removeAllBuildingsFromPerson(person);

        for (BuildingResponseDTO building : personDTO.accessibleBuildings()) {
            assignBuildingToPerson(person.getId(), building.getId());
        }
    }

    public void assignBuildingToPerson(Long personId, Long buildingId) {
        Person person = findPersonByIdStrict(personId);
        Building building = buildingService.findBuildingByIdStrict(buildingId);

        PersonBuilding personBuilding = new PersonBuilding();
        personBuilding.setPersonBuildingId(new PersonBuildingId(person, building));

        personBuildingRepository.save(personBuilding);
    }

    public void removeBuildingFromPerson(Long personId, Long buildingId) {
        Person person = findPersonByIdStrict(personId);
        Building building = buildingService.findBuildingByIdStrict(buildingId);

        Optional<PersonBuilding> personBuilding = personBuildingRepository
                .findByPersonBuildingId(new PersonBuildingId(person, building));

        personBuilding.ifPresent(value -> personBuildingRepository.delete(value));
    }

    public void removeAllBuildingsFromPerson(Person person) {
        List<PersonBuilding> buildings = personBuildingRepository.findAllByPersonBuildingId_Person(person);
        personBuildingRepository.deleteAll(buildings);
    }

    public List<PersonWithAccessResponseDTO> findAllPeople(Role type, String query) {
        List<Person> personList = personRepository.findAll(buildSpecification(type, query));

        return personList.stream()
                .map(person -> new PersonWithAccessResponseDTO(
                        personMapper.toResponseDTO(person),
                        buildingService.findAllBuildingsForPerson(person)
                ))
                .collect(Collectors.toList());
    }

    private Specification<Person> buildSpecification(Role type, String query) {
        Specification<Person> specification = PersonSpecification.noSpecifiation();

        if (type != null) {
            specification = specification.and(PersonSpecification.hasRole(type));
        }
        if (query != null && !query.isBlank()) {
            Specification<Person> searchSpecification = PersonSpecification.emailContains(query);
            searchSpecification = searchSpecification.or(PersonSpecification.codeContains(query));
            searchSpecification = searchSpecification.or(PersonSpecification.firstNameContains(query));
            searchSpecification = searchSpecification.or(PersonSpecification.lastNameContains(query));

            specification = specification.and(searchSpecification);
        }

        return specification;
    }

}
