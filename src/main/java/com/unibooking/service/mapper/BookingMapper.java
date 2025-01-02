package com.unibooking.service.mapper;

import com.unibooking.domain.Booking;
import com.unibooking.service.dto.BookingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "start", expression = "java(toLocalDateTime(dto.getDate(), dto.getStartTime()))")
    @Mapping(target = "end", expression = "java(toLocalDateTime(dto.getDate(), dto.getEndTime()))")
    Booking toEntity(BookingDTO dto);

    @Mapping(target = "roomCode", source = "room.code")
    @Mapping(target = "date", expression = "java(booking.getStart().toLocalDate())")
    @Mapping(target = "startTime", expression = "java(booking.getStart().toLocalTime())")
    @Mapping(target = "endTime", expression = "java(booking.getEnd().toLocalTime())")
    BookingDTO toDto(Booking booking);

    default LocalDateTime toLocalDateTime(LocalDate date, LocalTime time) {
        return date.atTime(time);
    }

}
