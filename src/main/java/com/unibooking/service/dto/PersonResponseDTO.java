package com.unibooking.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonResponseDTO {
    Long id;
    String firstName;
    String lastName;
    String email;
    String role;
    String code;
}
