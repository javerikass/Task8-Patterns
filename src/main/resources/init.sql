CREATE SCHEMA IF NOT EXISTS clevertec_system;

SET search_path TO clevertec_system;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id         UUID DEFAULT uuid_generate_v4(),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    mail       VARCHAR(255),
    age        INT,
    PRIMARY KEY (id)

);

INSERT INTO users (id, first_name, last_name, mail, age)
VALUES ('37bc2b04-8607-11ee-b9d1-0242ac120002', 'John', 'Doe', 'john.doe@mail.ru', 40),
       ('3ef4c1f6-8607-11ee-b9d1-0242ac120002', 'Jane', 'Smith', 'jane.smith@gmail.com', 30),
       ('43c4ec92-8607-11ee-b9d1-0242ac120002', 'Alice', 'Johnson', 'alice.johnson@gmail.com', 28);