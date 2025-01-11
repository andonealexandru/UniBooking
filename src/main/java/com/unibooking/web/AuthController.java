package com.unibooking.web;

import com.unibooking.security.JwtService;
import com.unibooking.security.UserInfoService;
import com.unibooking.service.AuthenticationService;
import com.unibooking.service.PersonService;
import com.unibooking.service.dto.AuthRequest;
import com.unibooking.service.dto.PersonDTO;
import com.unibooking.service.dto.PersonResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final PersonService personService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/current-user")
    public PersonResponseDTO retrieveCurrentUserData() {
        return authenticationService.getCurrentUserDTO();
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> addNewUser(@RequestBody PersonDTO person) {
        personService.createPerson(person);
        return ResponseEntity.noContent().build();
    }
}
