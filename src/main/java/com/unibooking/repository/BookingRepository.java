package com.unibooking.repository;

import com.unibooking.domain.Booking;
import com.unibooking.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room = :room " +
            "AND b.start < :endDate AND b.end > :startDate")
    boolean existsByRoomAndTimeOverlap(@Param("room") Room room,
                                       @Param("startDate") LocalDateTime start,
                                       @Param("endDate") LocalDateTime end);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.room = :room " +
            "AND CAST(b.start AS date) >= :queryDate " +
            "AND CAST(b.end AS date) <= :queryDate " +
            "ORDER BY b.start ASC, b.end ASC")
    List<Booking> findAllByRoomAndDate(@Param("room") Room room,
                                       @Param("queryDate") LocalDate date);
}
