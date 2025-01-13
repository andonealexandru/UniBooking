package com.unibooking.web;

import com.unibooking.domain.enumeration.WorkstationType;
import com.unibooking.exception.StartAfterEndException;
import com.unibooking.service.BookingService;
import com.unibooking.service.RoomFinderService;
import com.unibooking.service.RoomService;
import com.unibooking.service.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;
    private final RoomFinderService roomFinderService;

    @PostMapping
    private ResponseEntity<Void> createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        roomService.createRoom(roomDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    private ResponseEntity<Page<RoomDTO>> getRoomsPage(Pageable pageable) {
        return ResponseEntity.ok(roomService.findAllRooms(pageable));
    }

    @GetMapping("/{id}/bookings")
    private ResponseEntity<List<BookingResponseWithPersonDTO>> getAllBookingsInDayRoom(@PathVariable Long id, @RequestParam LocalDate date) {
        return ResponseEntity.ok(bookingService.findAllBookingsForRoomAndDate(id, date));
    }

    @GetMapping("/{id}/bookings/last")
    private ResponseEntity<BookingResponseWithPersonDTO> getLastBookingBeforeDateForRoom(
            @PathVariable Long id, @RequestParam @NonNull LocalDateTime before) {

        Optional<BookingResponseWithPersonDTO> response = bookingService.findLastBookingBeforeDateForRoom(id, before);

        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}/bookings/first")
    private ResponseEntity<BookingResponseWithPersonDTO> getFirstBookingAfterDateForRoom(
            @PathVariable Long id, @RequestParam @NonNull LocalDateTime after) {

        Optional<BookingResponseWithPersonDTO> response = bookingService.findFirstBookingAfterDateForRoom(id, after);

        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}/bookings/between")
    private ResponseEntity<List<BookingResponseWithPersonDTO>> getAllBookingsBetweenDatesForRoom(
            @PathVariable Long id, @RequestParam @NonNull LocalDateTime start, @RequestParam @NonNull LocalDateTime end) {

        if (start.isAfter(end))
            throw new StartAfterEndException("Start date should be before end date");

        return ResponseEntity.ok(bookingService.findAllBookingsBetweenDatesForRoom(id, start, end));
    }

    @GetMapping("/workstation-types")
    private ResponseEntity<List<WorkstationType>> getAllWorkstationTypes() {
        return ResponseEntity.ok(List.of(WorkstationType.values()));
    }

    @GetMapping("/find")
    private ResponseEntity<List<EmptyRoomResponseDTO>> findRoom(@Valid EmptyRoomRequestDTO request) {
        return ResponseEntity.ok(roomFinderService.findAllRoomsWithSlots(request));
    }
}
