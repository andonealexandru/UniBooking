package com.unibooking.service;

import com.unibooking.domain.Booking;
import com.unibooking.domain.Person;
import com.unibooking.domain.Room;
import com.unibooking.repository.BookingRepository;
import com.unibooking.service.dto.BookingDTO;
import com.unibooking.service.mapper.BookingMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final RoomService roomService;
    private final PersonService personService;

    public void createBooking(BookingDTO bookingDTO) {
        Room room = roomService.findRoomByCodeStrict(bookingDTO.getRoomCode());
        Person person = personService.getCurrentUser();

        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setRoom(room);
        booking.setPerson(person);

        bookingRepository.save(booking);
    }
}
