package com.unibooking.web;

import com.unibooking.service.BookingActionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingActionController {

    private BookingActionService bookingActionService;

    @PatchMapping("/{id}/check-in")
    private ResponseEntity<Void> bookingCheckIn(@PathVariable Long id) {
        bookingActionService.checkIn(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/check-out")
    private ResponseEntity<Void> bookingCheckOut(@PathVariable Long id) {
        bookingActionService.checkOut(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    private ResponseEntity<Void> bookingCancel(@PathVariable Long id) {
        bookingActionService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
