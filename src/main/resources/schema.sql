create TABLE if NOT EXISTS owner
(
    id        INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255),
    address   VARCHAR(255),
    telephone VARCHAR(50)
);

create TABLE IF NOT EXISTS pet
(
    id        INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255),
    pet_type  VARCHAR(255),
    age       INTEGER,
    weight    INTEGER,
    birthdate TIMESTAMP,
    owner_id  INTEGER,
    FOREIGN KEY (owner_id) REFERENCES owner (id)
);

create TABLE if NOT EXISTS vet
(
    id         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(255),
    speciality VARCHAR(255)
);

create TABLE if NOT EXISTS visit
(
    id        INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    diagnosis VARCHAR(255),
    price     INTEGER,
    date      TIMESTAMP,
    pet_id    INTEGER,
    vet_id    INTEGER,
    FOREIGN KEY (pet_id) REFERENCES pet (id),
    FOREIGN KEY (vet_id) REFERENCES vet (id)
);