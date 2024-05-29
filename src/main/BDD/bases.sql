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
VALUES (1, 'b22c108e17dd0136ca2aaacc505c49890c4cf209b47c5117b0479a670958a921f601b62acd95eb3e5f4957013cfc5d1b08bd52e9ed9418d79cf87dc117b8c643', 'f25a123f46db6518'), --password123
       (2, '31185963552f9540fd93e9d80a5d229da85dea095e3f5ba90bc59db8f792fd8340dc5681e460fa9ef480d6d8c1a410f2a4d674fa9b993cb4d6a6b7b46fb3e7a1', '27fc8c1371cde837'), --password456
       (3, '30f216bdd64fa5f49962aeac1e999acdf31e37f405268bf55699f7ce6f9601ab12238dd5a4414350a1607347263d36f7edf830ce2d052fc794fe99cfb1ec8a23', 'c2ac666b74031b7a'), --password789
       (4, '5ad8fd6aff1ab9bf6b49522c7c710cc798fcd2a6fcbde846f6e2fc24f0a25b16dad640e41db16b4dc2edd8f58787ad25db965d9bd3ade3c83144683ed46f4134', '8a2578990e6817e7'), --password101
       (5, '48cfcaab0e9f6ff55864cfedd945e522519b3868533e5866c5f4a9d548ddf056671ee813a05be77a9ad55517a899d45fe5fb9957a077352de19d992e5172cd8f', '7627a414ede42f7c'), --password202
       (6, 'd324298715c9e3f318d2b4c697ea82d2e36957248e18344032dfd22239c700a2c4ceeaa06f3ef30e05270f4b53f6f520f3ae9ccf06c312a525c0e6b21bba3124', 'b8f08f593e36c85a'), --password303
       (7, '093c5c5f6508d670027f07d5d521f388093bb6245a62d4e3868330254bf45c69a256cf7e1f38636ba2cae5fcd2a13ae40bcfad109f86a0148aac2c7d0eba8bb7', 'c3fa8aa91ad50920'), --password404
       (8, 'bf9692884b1e67b45719ab99aa475c1b2abbebe0bab1fcd901459318706d60870ff19496c311cc1a5247481ee71ff8a735aebfa354a8b40e1b423fb95b5fc650', 'afa123520d9739e9'), --password505
       (9, 'b80fe5f9fda1ae4ae53ebed79ed581025e58e35533b257260228f36c6a19da2992f38b45b7d4a328f641773b19ee496a3ad3603caae2a5cd128f3f73dd4f8c24', '59968d69477e258d'), --password606
       (10,	'5335fba3f9aed96b8bfb58c4a1b620c4ebe27ebadb9835465f8d5ad8b8d272005603bc7e1479fc3042a3918f8059344aa9d4ed4b2db7b0e453d4bc10c04f7b42', '6b06138f183249a3'); --password707
