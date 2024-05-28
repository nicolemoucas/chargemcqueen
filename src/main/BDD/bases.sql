CREATE TYPE EEtatBorne AS ENUM('disponible', 'indisponible', 'réservée', 'occupée');

CREATE TYPE EtypeImmat AS ENUM('Temporaire', 'Normale');

CREATE TYPE ETypeReservation AS ENUM('unique', 'garantie');

CREATE TABLE Client (
    idClient SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    adresse VARCHAR(200) NOT NULL,
    numTelephone CHAR(10) NOT NULL,
    motDePasse VARCHAR(50) NOT NULL,
    numCarte CHAR(16) NOT NULL
);

CREATE TABLE Facture (
    idFacture SERIAL PRIMARY KEY,
    idClient INT NOT NULL,
    prix float,
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);

CREATE TABLE Immatriculation (
    idImmat SERIAL PRIMARY KEY,
    numeroImmat CHAR(7) NOT NULL,
    typeImmat  EtypeImmat NOT NULL
);

CREATE TABLE Utilise (
    idUtilise SERIAL PRIMARY KEY,
    idClient INT NOT NULL,
    idImmat  INT NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(idClient),
    FOREIGN KEY (idImmat) REFERENCES Immatriculation(idImmat)
);

CREATE TABLE BorneRecharge (
    idBorne SERIAL PRIMARY KEY,
    etatBorne EEtatBorne NOT NULL
);

CREATE TABLE Reservation (
    idReservation SERIAL PRIMARY KEY,
    idImmat INT NOT NULL,
    idBorne INT NOT NULL,
    heureDebut TIMESTAMP NOT NULL,
    heureFin TIMESTAMP NOT NULL,
    type  EtypeImmat NOT NULL,
    nbProlongations INT NOT NULL,
    FOREIGN KEY (idImmat) REFERENCES Immatriculation(idImmat),
    FOREIGN KEY (idBorne) REFERENCES BorneRecharge(idBorne)
);

--              Insertions

-- Insérer les clients
INSERT INTO Client (nom, prenom, adresse, numTelephone, numCarte, motDePasse) VALUES
('Dupont', 'Jean', '123 Rue de Paris, 75001 Paris', '0102030405', '1234567812345678', 'hashedMdp'),
('Martin', 'Marie', '456 Avenue des Champs, 75002 Paris', '0102030406', '2345678923456789', 'hashedMdp'),
('Bernard', 'Luc', '789 Boulevard Saint-Michel, 75005 Paris', '0102030407', '3456789034567890', 'hashedMdp'),
('Dubois', 'Claire', '101 Rue de Rivoli, 75004 Paris', '0102030408', '4567890145678901', 'hashedMdp'),
('Thomas', 'Pierre', '202 Rue Saint-Honoré, 75001 Paris', '0102030409', '5678901256789012', 'hashedMdp'),
('Petit', 'Julien', '303 Rue Lafayette, 75010 Paris', '0102030410', '6789012367890123', 'hashedMdp'),
('Robert', 'Sophie', '404 Rue Oberkampf, 75011 Paris', '0102030411', '7890123478901234', 'hashedMdp'),
('Richard', 'Emilie', '505 Rue de la Pompe, 75016 Paris', '0102030412', '8901234589012345', 'hashedMdp'),
('Durand', 'Antoine', '606 Rue de Rennes, 75006 Paris', '0102030413', '9012345690123456', 'hashedMdp'),
('Lefevre', 'Nathalie', '707 Rue du Faubourg Saint-Antoine, 75012 Paris', '0102030414', '0123456701234567', 'hashedMdp');

