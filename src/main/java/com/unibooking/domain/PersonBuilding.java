package com.unibooking.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "person_building")
@Data
public class PersonBuilding {

    @EmbeddedId
    private PersonBuildingId personBuildingId;

}
