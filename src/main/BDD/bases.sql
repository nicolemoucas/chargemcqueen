CREATE TYPE EEtatBorne AS ENUM('disponible', 'indisponible', 'réservée', 'occupée');

CREATE TYPE EtypeImmat AS ENUM('Temporaire', 'Normale');

CREATE TYPE ETypeReservation AS ENUM('unique', 'garantie');

CREATE TABLE Client
(
    idClient     SERIAL PRIMARY KEY,
    nom          VARCHAR(50)  NOT NULL,
    prenom       VARCHAR(50)  NOT NULL,
    adresse      VARCHAR(200) NOT NULL,
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

INSERT INTO Client (nom, prenom, adresse, numTelephone, numCarte)
VALUES ('Dupont', 'Jean', '123 Rue de Paris, 75001 Paris', '0102030405', '1234567812345678'),
       ('Martin', 'Marie', '456 Avenue des Champs, 75002 Paris', '0102030406', '2345678923456789'),
       ('Bernard', 'Luc', '789 Boulevard Saint-Michel, 75005 Paris', '0102030407', '3456789034567890'),
       ('Dubois', 'Claire', '101 Rue de Rivoli, 75004 Paris', '0102030408', '4567890145678901'),
       ('Thomas', 'Pierre', '202 Rue Saint-Honoré, 75001 Paris', '0102030409', '5678901256789012'),
       ('Petit', 'Julien', '303 Rue Lafayette, 75010 Paris', '0102030410', '6789012367890123'),
       ('Robert', 'Sophie', '404 Rue Oberkampf, 75011 Paris', '0102030411', '7890123478901234'),
       ('Richard', 'Emilie', '505 Rue de la Pompe, 75016 Paris', '0102030412', '8901234589012345'),
       ('Durand', 'Antoine', '606 Rue de Rennes, 75006 Paris', '0102030413', '9012345690123456'),
       ('Lefevre', 'Nathalie', '707 Rue du Faubourg Saint-Antoine, 75012 Paris', '0102030414', '0123456701234567');

-- Insérer les comptes pour les clients
INSERT INTO Compte (idClient, motDePasse, sel)
VALUES (1, 'password123', 'salt123'),
       (2, 'password456', 'salt456'),
       (3, 'password789', 'salt789'),
       (4, 'password101', 'salt101'),
       (5, 'password202', 'salt202'),
       (6, 'password303', 'salt303'),
       (7, 'password404', 'salt404'),
       (8, 'password505', 'salt505'),
       (9, 'password606', 'salt606'),
       (10,	'password707', 'salt707');