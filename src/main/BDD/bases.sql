CREATE TYPE EEtatBorne AS ENUM('disponible', 'indisponible', 'réservée', 'occupée');

CREATE TYPE EtypeImmat AS ENUM('Temporaire', 'Normale');

CREATE TYPE ETypeReservation AS ENUM('unique', 'garantie');

CREATE TABLE Client
(
    idClient     SERIAL PRIMARY KEY,
    nom          VARCHAR(50)  NOT NULL,
    prenom       VARCHAR(50)  NOT NULL,
    email      VARCHAR(200) NOT NULL,
    numTelephone CHAR(10)     NOT NULL,
    numCarte     CHAR(16)     NOT NULL
);

CREATE TABLE Compte
(
    idCompte   SERIAL PRIMARY KEY,
    idClient   INT         NOT NULL,
    motDePasse VARCHAR(150) NOT NULL,
    sel        VARCHAR(50) NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client (idClient)
);

CREATE TABLE Facture
(
    idFacture SERIAL PRIMARY KEY,
    idClient  INT NOT NULL,
    prix      float,
    FOREIGN KEY (idClient) REFERENCES Client (idClient)
);

CREATE TABLE Immatriculation
(
    idImmat     SERIAL PRIMARY KEY,
    numeroImmat CHAR(7)    NOT NULL,
    typeImmat   EtypeImmat NOT NULL
);

CREATE TABLE Utilise
(
    idUtilise SERIAL PRIMARY KEY,
    idClient  INT NOT NULL,
    idImmat   INT NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client (idClient),
    FOREIGN KEY (idImmat) REFERENCES Immatriculation (idImmat)
);

CREATE TABLE BorneRecharge
(
    idBorne   SERIAL PRIMARY KEY,
    etatBorne EEtatBorne NOT NULL
);

CREATE TABLE Reservation
(
    idReservation   SERIAL PRIMARY KEY,
    idImmat         INT        NOT NULL,
    idBorne         INT        NOT NULL,
    heureDebut      TIMESTAMP  NOT NULL,
    heureFin        TIMESTAMP  NOT NULL,
    type            ETypeReservation NOT NULL,
    nbProlongations INT        NOT NULL,
    FOREIGN KEY (idImmat) REFERENCES Immatriculation (idImmat),
    FOREIGN KEY (idBorne) REFERENCES BorneRecharge (idBorne)
);

--              Insertions

INSERT INTO Client (nom, prenom, email, numTelephone, numCarte)
VALUES ('Dupont', 'Jean', 'j@dupont.fr', '0102030405', '1234567812345678'),
       ('Martin', 'Marie', 'm@marie.fr', '0102030406', '2345678923456789'),
       ('Bernard', 'Luc', 'l@bernard.com', '0102030407', '3456789034567890'),
       ('Dubois', 'Claire', 'claire@dubois.eu', '0102030408', '4567890145678901'),
       ('Thomas', 'Pierre', 'pierre@thomas.fr', '0102030409', '5678901256789012'),
       ('Petit', 'Julien', 'julien.petit@petit.com', '0102030410', '6789012367890123'),
       ('Robert', 'Sophie', 's.robert@cool.fr', '0102030411', '7890123478901234'),
       ('Richard', 'Emilie', 'em123@richard.com', '0102030412', '8901234589012345'),
       ('Durand', 'Antoine', 'antoined@durand.co', '0102030413', '9012345690123456'),
       ('Lefevre', 'Nathalie', 'nath@lefevre.lu', '0102030414', '0123456701234567');

-- Insérer les comptes pour les clients
INSERT INTO Compte (idClient, motDePasse, sel)
VALUES (1, 'b22c108e17dd0136ca2aaacc505c49890c4cf209b47c5117b0479a670958a921f601b62acd95eb3e5f4957013cfc5d1b08bd52e9ed9418d79cf87dc117b8c643', 'f25a123f46db6518'), -- password123
       (2, '4623b7627d0ffc1c89d87cf40b57e22c8dafd5146598049968b922c599e8f6bd9af2eabcf8b6e95b0358fc30556685c9f99d6b26e6b57a313d171297bc8cb2f3', '5685557be6ef49d49c5eaa4c14aaf945'), -- Ch1ck3nNug
       (3, '2acabd5dcd6a30719c62257bcc21c04bc9d2191a055c8b633aac4b96b5a6a461455ed4b925e66e2e9a181c7744d7abfdfd816ca143c6284015de56df5d8b36e3', '0c769acd5766cb75d494fdc4c24d85af'), -- M1cK3yM0us3
       (4, '5056173d0a51b664efbb160a9822b7bc399f533a99b836ef6e061a6b2e4ffc9a9af63ec14c1498db538dd62a4ea9e904d986c21443c753502be68576053a5e99', '45f152eb9017651f83b4d9e817901988'), -- N1nj4Turtl3
       (5, 'c4bd11cdf892d105a5c23f154925ff15e6d29594ec036c89ee683721f0575ce0e9f9fe0aa9a04390052f0740679ee23244c9b079bfc5279d2e2e56d58bc96fa4', 'e3e73e383ef3b324c26917d782198627'), -- V1k1ng50fV1k1ngs!Vr00m
       (6, 'c87de1a33742df84c201d80be19dbf879241a5f7794c6e1732f0860b72eab3de55760e8649668cfa76bbe819092812b9d3003eb5837ac70b1975e7b08ccb71c0', 'c5406ac31913501934967dcad51414f2'), -- A11ig4t0r!
       (7, 'd548c733092523a51f68d7e062f01fcf7428c132617e5597e0eaee81fffbf47b4c0e0c5e1852dd6cbafa70f2c4b922acbd49d2c9f6111b033e18173f621a1386', '139516a8f96d562c4dfc451fc520aa12'), -- blaBl3bl1bl0blu
       (8, '72121b700d094441d06419b48e59d4e50740393f81c3698437ad8f7666f4bae85378f532778cc9d0c65e378774a80f24b48d4e037d75032470bd7631e41fb94c', 'be6926eb3a1714c22da2c858de3a08ec'), -- !H4d$h0t
       (9, 'c6b4d5636678f9ae72b82004996cb7daf319a89b4d7915a756f653c23c812504319e522f0e868816c0fd49c6eb5431adebfd54e4b444577ef975407ff37bb32e', '922b9c1d626378f6a5aafdb6f9799f07'), -- P1zz4P1zz4
       (10,	'b2f945c05333a6f8b4e1328de35288e7f1eb6ce95f7ddaa6ab3ae076eed954869755410b1f97eedaff114a7c7dd69853b1f03c05e46fcac016fe1e96f7537132', '9082d5548d52225269b9788196a3a735'); -- F1zzB33r!!B33r

