CREATE SEQUENCE room_seq START WITH 10000 INCREMENT BY 1;

CREATE TABLE room (
    id          BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('room_seq'),
    code        VARCHAR(255) NOT NULL,
    capacity    INTEGER,
    building_id    BIGINT NOT NULL,

    CONSTRAINT fk_room_building FOREIGN KEY (building_id) REFERENCES building(id)
);