package com.unibooking.service;

import com.unibooking.domain.Person;
import com.unibooking.exception.PersonNotFoundException;
import com.unibooking.repository.PersonRepository;
import com.unibooking.service.dto.PersonDTO;
import com.unibooking.service.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;
    private PersonMapper personMapper;

    public Optional<Person> findPersonByCode(String code) {
        return personRepository.findByCode(code);
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException("No person with email " + email + " found!"));
    }

    public void createPerson(PersonDTO personDTO) {
        Person person = personMapper.toEntity(personDTO);

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");

        personRepository.save(person);
    }

}
