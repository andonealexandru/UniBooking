package com.unibooking.service;

import com.unibooking.domain.Person;
import com.unibooking.exception.PersonNotFoundException;
import com.unibooking.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;

    public Person getCurrentUser() {
        return personRepository
                .findById(10000L)
                .orElseThrow(() -> new PersonNotFoundException("Current user not found"));
    }

}
