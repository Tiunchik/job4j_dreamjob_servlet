CREATE TABLE IF NOT EXISTS CITIES
(
    usercities varchar(100) UNIQUE
);

INSERT INTO CITIES (usercities)
VALUES ('Saint-Peterburg'),
       ('Moscow'),
       ('Veliky Novgorod'),
       ('Pskov'),
       ('Bryansk');