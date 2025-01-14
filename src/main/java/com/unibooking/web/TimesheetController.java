package com.unibooking.web;

import com.unibooking.service.TimesheetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/timesheet")
@AllArgsConstructor
public class TimesheetController {

    private final TimesheetService timesheetService;

    @PostMapping
    public ResponseEntity<String> uploadTimesheet(@RequestParam LocalDate start, @RequestParam LocalDate end,
                                                  @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("Empty file");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Log file details
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        long fileSize = file.getSize();

        log.info("Received file: Name={}, Type={}, Size={}", fileName, fileType, fileSize);

        // Validate file type by extension
        if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
            log.error("Unsupported file type: {}", fileType);
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        if (start.getDayOfWeek() != DayOfWeek.MONDAY || end.getDayOfWeek() != DayOfWeek.SUNDAY) {
            log.error("Start must be MONDAY and end must be SUNDAY");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try (InputStream is = file.getInputStream()) {
            timesheetService.parseTimesheet(start, end, is);

            // get the list of all the object or check if the object is present in database if not present then add
            // else tell that this entry is already present
            //String msg = userService.registerUserByExcel(objects);
            return new ResponseEntity<>("msg", HttpStatus.OK);

        } catch (IOException e) {
            log.error("IOException occurred while processing file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
