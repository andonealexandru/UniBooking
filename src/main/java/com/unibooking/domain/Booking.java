package com.unibooking.domain;

import com.unibooking.domain.enumeration.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq_gen")
    @SequenceGenerator(name = "booking_seq_gen", sequenceName = "booking_seq", allocationSize = 1)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    @Column(name = "description")
    private String description;
}
