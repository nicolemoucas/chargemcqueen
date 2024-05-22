CREATE DATABASE db_charge_mcqueen;

USE db_charge_mcqueen;

CREATE TYPE EEtatBorne AS ENUM('disponible', 'indisponible', 'réservée', 'occupée');

CREATE TYPE EtypeImmat AS ENUM('Temporaire', 'Normale');

CREATE TYPE ETypeReservation AS ENUM('unique', 'garantie');

CREATE TABLE Client (
    idClient INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    adresse VARCHAR(200) NOT NULL,
    numTelephone ,CHAR(10) NOT NULL
    numCarte : CHAR(16) NOT NULL
);

CREATE TABLE Compte (
    idCompte INT AUTO_INCREMENT PRIMARY KEY,
    idClient INT NOT NULL,
    identifiant VARCHAR(50) NOT NULL,
    motDePasse VARCHAR(50) NOT NULL,
    sel VARCHAR(50) NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

CREATE TABLE Facture (
    idFacture INT AUTO_INCREMENT PRIMARY KEY,
    idClient INT NOT NULL,
    prix float,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

CREATE TABLE Immatriculation (
    idImmat INT AUTO_INCREMENT PRIMARY KEY,
    idClient INT,
    numeroImmat INT NOT NULL,
    typeImmat  EtypeImmats NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

CREATE TABLE BorneRecharge (
    idBorne INT AUTO_INCREMENT PRIMARY KEY,
    etatBorne EEtatBorne NOT NULL
);

CREATE TABLE Reservation (
    idReservation INT AUTO_INCREMENT PRIMARY KEY,
    idImmat INT NOT NULL,
    idBorne INT NOT NULL,
    heureDebut DateTime NOT NULL,
    heureFin DateTime NOT NULL,
    type  EtypeImmat NOT NULL,
    nbProlongations INT NOT NULL,
    FOREIGN KEY (idImmat) REFERENCES Immatriculation(idImmat),
    FOREIGN KEY (idBorne) REFERENCES BorneRecharge(idBorne)
);