package com.unibooking.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingResponseWithPersonDTO {

    @JsonUnwrapped
    BookingResponseDTO bookingResponseDTO;

    @JsonProperty("person")
    PersonResponseDTO personResponseDTO;
}
