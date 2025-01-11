package com.unibooking.service.scheduler;

import com.unibooking.domain.enumeration.BookingStatus;
import com.unibooking.repository.BookingRepository;
import com.unibooking.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class BookingStatusScheduler {

    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void finishReservations() {
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = bookingRepository
                .updateReservationsWithEndBeforeDate(now, BookingStatus.CHECKED_IN, BookingStatus.FINISHED);

        log.info("Automatically finished " + updatedCount + " reservations");
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelReservations() {
        LocalDateTime now = LocalDateTime.now().minusMinutes(BookingService.BOOKING_CHECK_IN_WINDOW);
        int updatedCount = bookingRepository
                .updateReservationsWithStartBeforeDate(now, BookingStatus.PENDING, BookingStatus.CANCELED);

        log.info("Automatically canceled " + updatedCount + " reservations");
    }
}
