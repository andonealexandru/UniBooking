package com.unibooking.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room")
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq_gen")
    @SequenceGenerator(name = "room_seq_gen", sequenceName = "room_seq", allocationSize = 1)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "building_id")
    private Building building;

    //TODO: idea: number of workstations, workstations type

}
