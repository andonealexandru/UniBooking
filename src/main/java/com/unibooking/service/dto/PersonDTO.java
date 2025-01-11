package com.unibooking.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonDTO {
    String firstName;
    String lastName;
    String email;
    String password;
}
