CREATE SEQUENCE building_seq START WITH 10000 INCREMENT BY 1;

CREATE TABLE building (
    id          BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('building_seq') ,
    code        VARCHAR(255) NOT NULL,
    address     VARCHAR(255)
);