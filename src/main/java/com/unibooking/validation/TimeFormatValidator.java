package com.unibooking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeFormatValidator implements ConstraintValidator<ValidTimeFormat, Integer> {

    @Override
    public boolean isValid(Integer time, ConstraintValidatorContext context) {
        if (time == null) {
            return true;
        }

        int hour = time / 100;
        int minute = time % 100;

        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }
}