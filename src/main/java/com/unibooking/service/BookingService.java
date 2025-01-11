package com.unibooking.service;

import com.unibooking.domain.Booking;
import com.unibooking.domain.Person;
import com.unibooking.domain.Room;
import com.unibooking.domain.enumeration.BookingStatus;
import com.unibooking.exception.RoomNotAvailableException;
import com.unibooking.repository.BookingRepository;
import com.unibooking.service.dto.BookingDTO;
import com.unibooking.service.dto.BookingResponseDTO;
import com.unibooking.service.dto.BookingResponseWithPersonDTO;
import com.unibooking.service.mapper.BookingMapper;
import com.unibooking.service.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;
    private final PersonMapper personMapper;

    private final RoomService roomService;
    private final BuildingService buildingService;
    private final AuthenticationService authenticationService;

    public void createBooking(BookingDTO bookingDTO) {
        Booking booking = bookingMapper.toEntity(bookingDTO);

        Room room = roomService.findRoomByCodeStrict(bookingDTO.getRoomCode());
        if (!isRoomAvailableForInterval(room, booking.getStart(), booking.getEnd())) {
            throw new RoomNotAvailableException("Room " + room.getCode() + " is not available for the selected time slot!");
        }

        Person person = authenticationService.getCurrentUser();

        booking.setRoom(room);
        booking.setPerson(person);

        bookingRepository.save(booking);
    }

    public Boolean isRoomAvailableForInterval(Room room, LocalDateTime start, LocalDateTime end) {
        return !bookingRepository.existsByRoomAndTimeOverlap(room, start, end) &&
                buildingService.isBuildingAvailableForInterval(room.getBuilding(), start.toLocalTime(), end.toLocalTime());
    }

    public List<BookingResponseDTO> findAllBookingsForRoomAndDate(Long id, LocalDate date) {
        Room room = roomService.findRoomByIdStrict(id);

        return bookingRepository
                .findAllByRoomAndDate(room, date)
                .stream().map(bookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> findAllBookingsForDateAndCurrentUser(LocalDate date) {
        Person currentUser = authenticationService.getCurrentUser();

        return bookingRepository
                .findAllByPersonAndDate(currentUser, date)
                .stream().map(bookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<BookingResponseDTO> findBookingReadyForCheckInForCurrentUser() {
        Person currentUser = authenticationService.getCurrentUser();

        LocalDateTime start = LocalDateTime.now().minus(10, ChronoUnit.MINUTES);
        LocalDateTime end = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);

//        return bookingRepository
//                .findByPersonAndStartBetweenAndStatus(currentUser, start, end, BookingStatus.PENDING)
//                .map(bookingMapper::toResponseDto);

        return bookingRepository.findById(10016L).map(bookingMapper::toResponseDto);
    }

    public Optional<BookingResponseDTO> findActiveBookingForCurrentUser() {
        Person currentUser = authenticationService.getCurrentUser();
        LocalDateTime currentTime = LocalDateTime.now();

//        return bookingRepository
//                .findByPersonAndStartBeforeAndEndAfterAndStatus(currentUser, currentTime, currentTime, BookingStatus.CHECKED_IN)
//                .map(bookingMapper::toResponseDto);
        return bookingRepository.findById(10012L).map(bookingMapper::toResponseDto);
    }

    public Optional<BookingResponseWithPersonDTO> findLastBookingBeforeDateForRoom(Long roomId, LocalDateTime dateTime) {
        // finding only for day selected
        Room room = roomService.findRoomByIdStrict(roomId);
        LocalDateTime startOfDay = dateTime.toLocalDate().atTime(LocalTime.MIN);

        List<Booking> previousBookings = bookingRepository.findByRoomAndStatusNotAndEndBetweenOrderByEndDesc(
                room, BookingStatus.CANCELED, startOfDay, dateTime);

        if (previousBookings.isEmpty()) return Optional.empty();

        return Optional.of(BookingResponseWithPersonDTO.builder()
                .bookingResponseDTO(bookingMapper.toResponseDto(previousBookings.get(0)))
                .personResponseDTO(personMapper.toResponseDTO(previousBookings.get(0).getPerson()))
                .build());
    }

    public Optional<BookingResponseWithPersonDTO> findFirstBookingAfterDateForRoom(Long roomId, LocalDateTime dateTime) {
        // finding only for day selected
        Room room = roomService.findRoomByIdStrict(roomId);
        LocalDateTime endOfDay = dateTime.toLocalDate().atTime(LocalTime.MAX);

        List<Booking> nextBookings = bookingRepository.findByRoomAndStatusNotAndStartBetweenOrderByStartAsc(
                room, BookingStatus.CANCELED, dateTime, endOfDay);

        if (nextBookings.isEmpty()) return Optional.empty();

        return Optional.of(BookingResponseWithPersonDTO.builder()
                .bookingResponseDTO(bookingMapper.toResponseDto(nextBookings.get(0)))
                .personResponseDTO(personMapper.toResponseDTO(nextBookings.get(0).getPerson()))
                .build());
    }

    public List<BookingResponseWithPersonDTO> findAllBookingsBetweenDatesForRoom(
            Long roomId, LocalDateTime start, LocalDateTime end) {
        Room room = roomService.findRoomByIdStrict(roomId);

        return bookingRepository.findAllByRoomAndEndAfterAndStartBeforeAndStatusNotOrderByStartAsc(
                room, start, end, BookingStatus.CANCELED)
                .stream()
                .map(booking -> BookingResponseWithPersonDTO.builder()
                        .bookingResponseDTO(bookingMapper.toResponseDto(booking))
                        .personResponseDTO(personMapper.toResponseDTO(booking.getPerson()))
                        .build())
                .collect(Collectors.toList());
    }

    public void findRoomAvailableForInterval() {
        //TODO: implement
    }

    public void findAllBookingsForRoomForDay() {
        //TODO: implement
    }
}
