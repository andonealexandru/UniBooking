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

    default LocalDateTime toLocalDateTime(LocalDate date, LocalTime time) {
        return date.atTime(time);
    }

}
