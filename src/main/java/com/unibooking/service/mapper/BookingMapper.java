package com.unibooking.service.mapper;

import com.unibooking.domain.Booking;
import com.unibooking.service.dto.BookingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "start", expression = "java(toInstant(dto.getDate(), dto.getStartTime()))")
    @Mapping(target = "end", expression = "java(toInstant(dto.getDate(), dto.getEndTime()))")
    Booking toEntity(BookingDTO dto);

    default LocalDateTime toInstant(LocalDate date, Integer hourMinute) {
        int hours = hourMinute / 100;
        int minutes = hourMinute % 100;
        return date.atTime(hours, minutes);
    }

}
