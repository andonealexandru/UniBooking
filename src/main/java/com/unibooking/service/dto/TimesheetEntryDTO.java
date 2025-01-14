package com.unibooking.service.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record TimesheetEntryDTO (
        String roomCode,
        String teacherCode,
        boolean isOdd,
        DayOfWeek day,
        LocalTime startHour
) { }
