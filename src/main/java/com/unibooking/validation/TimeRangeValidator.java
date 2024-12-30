package com.unibooking.validation;

import com.unibooking.service.dto.BookingDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, BookingDTO> {

    @Override
    public boolean isValid(BookingDTO dto, ConstraintValidatorContext context) {
        if (dto.getStartTime() == null || dto.getEndTime() == null || dto.getDate() == null) {
            return true;
        }

        int startHour = dto.getStartTime();
        int endHour = dto.getEndTime();

        return startHour <= endHour;
    }
}