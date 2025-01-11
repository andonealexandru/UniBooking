package com.unibooking.web;

import com.unibooking.service.BookingService;
import com.unibooking.service.dto.BookingDTO;
import com.unibooking.service.dto.BookingResponseDTO;
import com.unibooking.service.dto.BookingResponseWithPersonDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    private ResponseEntity<Void> createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        bookingService.createBooking(bookingDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mine")
    private ResponseEntity<List<BookingResponseDTO>> retrieveBookingDayForCurrentUser(@RequestParam LocalDate date) {
        return ResponseEntity.ok(bookingService.findAllBookingsForDateAndCurrentUser(date));
    }

    @GetMapping("/mine/ready-for-check-in")
    private ResponseEntity<BookingResponseDTO> retrieveBookingReadyForCheckInForCurrentUser() {
        return bookingService
                .findBookingReadyForCheckInForCurrentUser()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/mine/active")
    private ResponseEntity<BookingResponseDTO> retrieveActiveBookingForCurrentUser() {
        return bookingService
                .findActiveBookingForCurrentUser()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
