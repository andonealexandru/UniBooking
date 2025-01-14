package com.unibooking.service;

import com.unibooking.service.dto.BookingDTO;
import com.unibooking.service.dto.BookingWithPersonDTO;
import com.unibooking.service.dto.TimesheetEntryDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class TimesheetService {

    private final BookingService bookingService;

    public void parseTimesheet(LocalDate start, LocalDate end, InputStream is) throws IOException {
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        List<TimesheetEntryDTO> timesheetEntryList = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() < 7) continue;
            timesheetEntryList.addAll(processRow(row));
        }

        List<BookingWithPersonDTO> bookings = timesheetEntryList.stream()
                .flatMap(t -> generateBookingsForTimesheetEntry(t, start, end).stream())
                .toList();

        int bookingsSaved = bookingService.processListOfBookings(bookings);

        log.info("Saved {} new reservations", bookingsSaved);

        workbook.close();
    }

    private List<BookingWithPersonDTO> generateBookingsForTimesheetEntry(TimesheetEntryDTO timesheetEntry, LocalDate start, LocalDate end) {
        // start is always monday
        // end is always sunday
        List<BookingWithPersonDTO> result = new ArrayList<>();
        LocalDate currentDate = LocalDate.from(start);

        if (!timesheetEntry.isOdd()) // if is odd is ok
            currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)); // if is even go next week

        if (timesheetEntry.day() != DayOfWeek.MONDAY)
            currentDate = currentDate.with(TemporalAdjusters.next(timesheetEntry.day()));

        for (; currentDate.isBefore(end); currentDate = currentDate.plusDays(14)) {
            result.add(new BookingWithPersonDTO(
                    BookingDTO.builder()
                            .date(currentDate)
                            .startTime(timesheetEntry.startHour())
                            .endTime(timesheetEntry.startHour().plusMinutes(110))
                            .roomCode(timesheetEntry.roomCode())
                            .build(),
                    timesheetEntry.teacherCode()
            ));
        }

        return result;
    }

    private List<TimesheetEntryDTO> processRow(Row row) {

        List<TimesheetEntryDTO> rowResults = new ArrayList<>();

        // first row with timesheets is 7
        // odd row => odd week
        // even row => even week

        for (int i = 4; i < row.getLastCellNum(); i++) {
            Pair<String, String> roomAndTeacher = extractRoomAndTeacherFromCell(row.getCell(i));
            if (roomAndTeacher == null) continue;
            String roomCode = roomAndTeacher.a;
            String teacherCode = roomAndTeacher.b;
            boolean isOddWeek = (row.getRowNum() % 2 == 0);

            Pair<DayOfWeek, LocalTime> dayOfWeekAndStartHour = HOURS_MAP.get(i);
            DayOfWeek dayOfWeek = dayOfWeekAndStartHour.a;
            LocalTime startHour = dayOfWeekAndStartHour.b;

            rowResults.add(new TimesheetEntryDTO(roomCode, teacherCode, isOddWeek, dayOfWeek, startHour));
            log.info("Room {}, Teacher {}, isOdd {}, day {}, startHour {}", roomCode, teacherCode, isOddWeek, dayOfWeek, startHour);
        }

        return rowResults;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private Pair<String, String> extractRoomAndTeacherFromCell(Cell cell) {
        String cellValue = getCellValueAsString(cell);

        if (cellValue.isBlank()) return null;

        String[] splitCellInfo =  cellValue.split(",");

        if (splitCellInfo.length != 4) {
            log.error(cellValue);
            return null;
        }

        return new Pair<>(splitCellInfo[2].trim(), splitCellInfo[3].trim());
    }

    private static final Map<Integer, Pair<DayOfWeek, LocalTime>> HOURS_MAP = Map.ofEntries(
            Map.entry(4, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(8, 0))),
            Map.entry(5, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(10, 0))),
            Map.entry(6, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(12, 0))),
            Map.entry(7, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(14, 0))),
            Map.entry(8, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(16, 0))),
            Map.entry(9, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(18, 0))),
            Map.entry(10, new Pair<>(DayOfWeek.MONDAY, LocalTime.of(20, 0))),

            Map.entry(11, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(8, 0))),
            Map.entry(12, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(10, 0))),
            Map.entry(13, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(12, 0))),
            Map.entry(14, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(14, 0))),
            Map.entry(15, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(16, 0))),
            Map.entry(16, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(18, 0))),
            Map.entry(17, new Pair<>(DayOfWeek.TUESDAY, LocalTime.of(20, 0))),

            Map.entry(18, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0))),
            Map.entry(19, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0))),
            Map.entry(20, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0))),
            Map.entry(21, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0))),
            Map.entry(22, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(16, 0))),
            Map.entry(23, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0))),
            Map.entry(24, new Pair<>(DayOfWeek.WEDNESDAY, LocalTime.of(20, 0))),

            Map.entry(25, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(8, 0))),
            Map.entry(26, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(10, 0))),
            Map.entry(27, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(12, 0))),
            Map.entry(28, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(14, 0))),
            Map.entry(29, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(16, 0))),
            Map.entry(30, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(18, 0))),
            Map.entry(31, new Pair<>(DayOfWeek.THURSDAY, LocalTime.of(20, 0))),

            Map.entry(32, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(8, 0))),
            Map.entry(33, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(10, 0))),
            Map.entry(34, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(12, 0))),
            Map.entry(35, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(14, 0))),
            Map.entry(36, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(16, 0))),
            Map.entry(37, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(18, 0))),
            Map.entry(38, new Pair<>(DayOfWeek.FRIDAY, LocalTime.of(20, 0))),

            Map.entry(39, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(8, 0))),
            Map.entry(40, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(10, 0))),
            Map.entry(41, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(12, 0))),
            Map.entry(42, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(14, 0))),
            Map.entry(43, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(16, 0))),
            Map.entry(44, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(18, 0))),
            Map.entry(45, new Pair<>(DayOfWeek.SATURDAY, LocalTime.of(20, 0)))
    );
}
