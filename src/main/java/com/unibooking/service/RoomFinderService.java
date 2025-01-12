package com.unibooking.service;

import com.unibooking.domain.Booking;
import com.unibooking.domain.Room;
import com.unibooking.repository.BookingRepository;
import com.unibooking.repository.RoomRepository;
import com.unibooking.service.dto.EmptyRoomRequestDTO;
import com.unibooking.service.dto.EmptyRoomResponseDTO;
import com.unibooking.service.mapper.RoomMapper;
import com.unibooking.service.specification.RoomSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RoomFinderService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    private final RoomMapper roomMapper;

    public List<EmptyRoomResponseDTO> findAllRoomsWithSlots(EmptyRoomRequestDTO request) {

        List<Room> rooms = roomRepository.findAll(buildRoomSpecification(request));

        List<EmptyRoomResponseDTO> response = new ArrayList<>();

        for (Room r : rooms) {
            response.addAll(findAllSlotsForRoom(r, request.date().atTime(request.start()), request.date().atTime(request.end())));
        }

        return response;
    }

    private Specification<Room> buildRoomSpecification(EmptyRoomRequestDTO request) {
        Specification<Room> specification = RoomSpecification.noSpecifiation();

        if (request.buildingCode() != null) {
            specification = specification.and(RoomSpecification.hasBuildingCode(request.buildingCode()));
        }

        if (request.roomCode() != null) {
            specification = specification.and(RoomSpecification.hasRoomCode(request.roomCode()));
        }

        if (request.workstationType() != null) {
            specification = specification.and(RoomSpecification.hasWorkstationType(request.workstationType()));
        }

        if (request.capacity() != null) {
            specification = specification.and(RoomSpecification.hasMinimumCapacity(request.capacity()));
        }

        if (request.workstationCount() != null) {
            specification = specification.and(RoomSpecification.hasMinimumWorkstationCount(request.workstationCount()));
        }

        return specification;
    }

    private List<EmptyRoomResponseDTO> findAllSlotsForRoom(Room room, LocalDateTime start, LocalDateTime end) {

        if (start.isBefore(start.toLocalDate().atTime(room.getBuilding().getStart())))
            start = start.toLocalDate().atTime(room.getBuilding().getStart());

        if (end.isAfter(end.toLocalDate().atTime(room.getBuilding().getEnd())))
            end = end.toLocalDate().atTime(room.getBuilding().getEnd());

        List<Booking> bookingsForDay = bookingRepository
                .findAllByRoomAndEndAfterAndStartBeforeAndStatusNotInOrderByStartAsc(room, start, end, BookingService.NON_ACTIVE_STATUSES);

        List<EmptyRoomResponseDTO> availableSlots = new ArrayList<>();

        if (bookingsForDay.isEmpty()) {
            availableSlots.add(new EmptyRoomResponseDTO(start, end, roomMapper.toResponseDto(room)));
            return availableSlots;
        }

        Booking first = bookingsForDay.get(0);
        Booking second = first;
        if (isGapBetween(start, first.getStart()))
            availableSlots.add(new EmptyRoomResponseDTO(start, first.getStart(), roomMapper.toResponseDto(room)));

        for (int i = 0; i < bookingsForDay.size() - 1; i++) {
            second = bookingsForDay.get(i+1);

            if (isGapBetween(first.getEnd(), second.getStart()))
                availableSlots.add(new EmptyRoomResponseDTO(first.getEnd(), second.getStart(), roomMapper.toResponseDto(room)));

            first = second;
        }

        if (isGapBetween(second.getEnd(), end))
            availableSlots.add(new EmptyRoomResponseDTO(second.getEnd(), end, roomMapper.toResponseDto(room)));

        return availableSlots;
    }

    private boolean isGapBetween(LocalDateTime start, LocalDateTime end) {
        return start.plusMinutes(5).isBefore(end);
    }

}
