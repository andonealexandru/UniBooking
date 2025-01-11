package com.unibooking.service;

import com.unibooking.domain.Booking;
import com.unibooking.domain.Person;
import com.unibooking.domain.enumeration.BookingStatus;
import com.unibooking.exception.UnauthorizedActionException;
import com.unibooking.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingActionService {

    private BookingRepository bookingRepository;

    private BookingService bookingService;
    private AuthenticationService authenticationService;

    public void checkIn(Long id) {
        Booking booking = bookingService.findBookingByIdStrict(id);

        authorizeAndPerformAction(booking, BookingStatus.PENDING, BookingStatus.CHECKED_IN);
    }

    public void checkOut(Long id) {
        Booking booking = bookingService.findBookingByIdStrict(id);

        authorizeAndPerformAction(booking, BookingStatus.CHECKED_IN, BookingStatus.FINISHED);
    }

    public void cancel(Long id) {
        Booking booking = bookingService.findBookingByIdStrict(id);

        authorizeCurrentUser(booking);
        changeBookingStatus(booking, BookingStatus.CANCELED);
    }

    private void authorizeAndPerformAction(Booking booking, BookingStatus oldStatus, BookingStatus newStatus) {
        authorizeCurrentUser(booking);

        if (booking.getStatus() != oldStatus) {
            throw new UnauthorizedActionException("You can't perform this action");
        }

        changeBookingStatus(booking, newStatus);
    }

    private void authorizeCurrentUser(Booking booking) {
        Person currentUser = authenticationService.getCurrentUser();

        if(booking.getPerson() != currentUser) {
            throw new UnauthorizedActionException("Only the creator of the reservation can perform this action");
        }
    }

    private void changeBookingStatus(Booking booking, BookingStatus newStatus) {
        booking.setStatus(newStatus);
        bookingRepository.save(booking);
    }
}
