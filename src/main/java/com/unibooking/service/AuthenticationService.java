package com.unibooking.service;

import com.unibooking.domain.Person;
import com.unibooking.repository.PersonRepository;
import com.unibooking.service.dto.PersonDTO;
import com.unibooking.service.dto.PersonResponseDTO;
import com.unibooking.service.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public Person getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        return personRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Username " + email + " not found!")
        );
    }

    public PersonResponseDTO getCurrentUserDTO() {
        return personMapper.toResponseDTO(getCurrentUser());
    }
}
