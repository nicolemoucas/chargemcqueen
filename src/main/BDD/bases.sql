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
    motDePasse VARCHAR(50) NOT NULL,
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
    type            EtypeImmat NOT NULL,
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
VALUES (1, 'password123', 'f25a123f46db6518'),
       (2, 'password456', '27fc8c1371cde837'),
       (3, 'password789', 'c2ac666b74031b7a'),
       (4, 'password101', '8a2578990e6817e7'),
       (5, 'password202', '7627a414ede42f7c'),
       (6, 'password303', 'b8f08f593e36c85a'),
       (7, 'password404', 'c3fa8aa91ad50920'),
       (8, 'password505', 'afa123520d9739e9'),
       (9, 'password606', '59968d69477e258d'),
       (10,	'password707', '6b06138f183249a3');