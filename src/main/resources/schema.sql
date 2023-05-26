create TABLE IF NOT EXISTS pet
(
    id         integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name       varchar(255),
    pet_type   varchar(255),
    age        integer,
    weight     integer,
    birthdate  timestamp
);

create TABLE if NOT EXISTS owner
(
    id           integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name         varchar(255),
    address      varchar(255),
    telephone    varchar(50)
);

create TABLE if NOT EXISTS vet
(
    id           integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name         varchar(255),
    speciality   varchar(255)
);

create TABLE if NOT EXISTS visit
(
    id           integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    diagnosis    varchar(255),
    price        integer,
    date         timestamp
);
