package com.unibooking.service.mapper;

import com.unibooking.domain.Booking;
import com.unibooking.service.dto.BookingDTO;
import com.unibooking.service.dto.BookingResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", imports = DateTimeFormatter.class)
public interface BookingMapper {

    @Mapping(target = "start", expression = "java(toLocalDateTime(dto.getDate(), dto.getStartTime()))")
    @Mapping(target = "end", expression = "java(toLocalDateTime(dto.getDate(), dto.getEndTime()))")
    Booking toEntity(BookingDTO dto);

    @Mapping(target = "roomCode", source = "room.code")
    @Mapping(target = "date", expression = "java(booking.getStart().toLocalDate())")
    @Mapping(target = "startTime", expression = "java(booking.getStart().toLocalTime())")
    @Mapping(target = "endTime", expression = "java(booking.getEnd().toLocalTime())")
    BookingDTO toDto(Booking booking);

    @Mapping(target = "roomCode", source = "room.code")
    @Mapping(target = "buildingCode", source = "room.building.code")
    @Mapping(target = "date", expression = "java(booking.getStart().toLocalDate())")
    @Mapping(target = "startTime", expression = "java(booking.getStart().toLocalTime().format(DateTimeFormatter.ofPattern(\"HH:mm\")))")
    @Mapping(target = "endTime", expression = "java(booking.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern(\"HH:mm\")))")
    BookingResponseDTO toResponseDto(Booking booking);

    default LocalDateTime toLocalDateTime(LocalDate date, LocalTime time) {
        return date.atTime(time);
    }
}
