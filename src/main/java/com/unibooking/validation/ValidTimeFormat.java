package com.unibooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeFormat {
    String message() default "Invalid time format. Must be in HHmm format (e.g., 1630 for 4:30 PM).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}