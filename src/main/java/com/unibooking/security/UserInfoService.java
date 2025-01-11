package com.unibooking.security;

import com.unibooking.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserInfoDetails(personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!")));
    }

}
