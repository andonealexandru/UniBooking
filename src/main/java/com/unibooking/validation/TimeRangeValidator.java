package com.unibooking.validation;

import com.unibooking.service.dto.BookingDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, BookingDTO> {

    @Override
    public boolean isValid(BookingDTO dto, ConstraintValidatorContext context) {
        if (dto.getStartTime() == null || dto.getEndTime() == null || dto.getDate() == null) {
            return true;
        }

        LocalTime startHour = dto.getStartTime();
        LocalTime endHour = dto.getEndTime();

        return startHour.isBefore(endHour);
    }
}