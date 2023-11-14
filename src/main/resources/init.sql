CREATE SCHEMA clevertec_system;

SET search_path TO  clevertec_system;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id         UUID DEFAULT uuid_generate_v4(),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    mail       VARCHAR(255),
    age        INT,
    PRIMARY KEY (id)

);


INSERT INTO users (id, first_name, last_name, mail, age)
VALUES ('11111111-1111-1111-1111-111111111111', 'John', 'Doe', 'john.doe@mail.ru', 25),
       ('22222222-2222-2222-2222-222222222222', 'Jane', 'Smith', 'jane.smith@gmail.com', 30),
       ('33333333-3333-3333-3333-333333333333', 'Alice', 'Johnson', 'alice.johnson@gmail.com',
        28);