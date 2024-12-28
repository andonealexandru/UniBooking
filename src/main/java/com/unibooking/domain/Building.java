package com.unibooking.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "building")
@Data
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_seq_gen")
    @SequenceGenerator(name = "building_seq_gen", sequenceName = "building_seq", allocationSize = 1)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "address")
    private String address;
}
