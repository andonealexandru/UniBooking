CREATE TABLE person_building (
    person_id       BIGINT NOT NULL,
    building_id     BIGINT NOT NULL,

    PRIMARY KEY (person_id, building_id),
    CONSTRAINT fk_person_building_person FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT fk_person_building_building FOREIGN KEY (building_id) REFERENCES building(id)
);