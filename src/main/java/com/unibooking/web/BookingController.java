package com.unibooking.web;

import com.unibooking.service.BookingService;
import com.unibooking.service.dto.BookingDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    private ResponseEntity<Void> createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        bookingService.createBooking(bookingDTO);
        return ResponseEntity.noContent().build();
    }
}
