package com.unibooking.service;

import com.unibooking.domain.Booking;
import com.unibooking.domain.Person;
import com.unibooking.domain.Room;
import com.unibooking.exception.RoomNotAvailableException;
import com.unibooking.repository.BookingRepository;
import com.unibooking.service.dto.BookingDTO;
import com.unibooking.service.mapper.BookingMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final RoomService roomService;
    private final PersonService personService;

    public void createBooking(BookingDTO bookingDTO) {
        Booking booking = bookingMapper.toEntity(bookingDTO);

        Room room = roomService.findRoomByCodeStrict(bookingDTO.getRoomCode());
        if (!isRoomAvailableForInterval(room, booking.getStart(), booking.getEnd())) {
            throw new RoomNotAvailableException("Room " + room.getCode() + " is not available for the selected time slot!");
        }

        Person person = personService.getCurrentUser();

        booking.setRoom(room);
        booking.setPerson(person);

        bookingRepository.save(booking);
    }

    public Boolean isRoomAvailableForInterval(Room room, LocalDateTime start, LocalDateTime end) {
        return !bookingRepository.existsByRoomAndTimeOverlap(room, start, end);
    }

    public void findRoomAvailableForInterval() {
        //TODO: implement
    }

    public void findAllBookingsForRoomForDay() {
        //TODO: implement
    }
}
