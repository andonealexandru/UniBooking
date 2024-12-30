CREATE SEQUENCE booking_seq START WITH 10000 INCREMENT BY 1;

CREATE TABLE booking (
    id          BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('booking_seq'),
    start_time  TIMESTAMP NOT NULL,
    end_time    TIMESTAMP NOT NULL,
    person_id   BIGINT,
    room_id     BIGINT NOT NULL,

    CONSTRAINT fk_booking_person FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES room(id)
);