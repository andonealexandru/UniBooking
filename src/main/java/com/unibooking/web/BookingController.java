package com.unibooking.web;

import com.unibooking.service.BookingService;
import com.unibooking.service.dto.BookingDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping
    private ResponseEntity<List<BookingDTO>> retrieveBookingDayForRoom(@RequestParam String roomCode, @RequestParam LocalDate date) {
        return ResponseEntity.ok(bookingService.findAllBookingsForRoomAndDate(roomCode, date));
    }
}
