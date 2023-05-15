CREATE TABLE IF NOT EXISTS pet
(
    id         integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name       varchar(255),
    pet_type       varchar(255),
    age        integer,
    weight     integer,
    birthdate  timestamp

);