package com.unibooking.service;

import com.unibooking.domain.Building;
import com.unibooking.domain.Person;
import com.unibooking.domain.PersonBuilding;
import com.unibooking.domain.PersonBuildingId;
import com.unibooking.domain.enumeration.Role;
import com.unibooking.exception.PersonNotFoundException;
import com.unibooking.repository.PersonBuildingRepository;
import com.unibooking.repository.PersonRepository;
import com.unibooking.service.dto.PersonDTO;
import com.unibooking.service.dto.PersonResponseDTO;
import com.unibooking.service.dto.PersonWithAccessResponseDTO;
import com.unibooking.service.mapper.BuildingMapper;
import com.unibooking.service.mapper.PersonMapper;
import lombok.AllArgsConstructor;
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

    public List<PersonWithAccessResponseDTO> findAllPeople(Optional<Role> type) {
        List<Person> personList;

        if (type.isPresent()) {
            personList = personRepository.findAllByRole(type.get());
        }
        else {
            personList = personRepository.findAll();
        }

        return personList.stream()
                .map(person -> new PersonWithAccessResponseDTO(
                        personMapper.toResponseDTO(person),
                        buildingService.findAllBuildingsForPerson(person)
                ))
                .collect(Collectors.toList());
    }

}
