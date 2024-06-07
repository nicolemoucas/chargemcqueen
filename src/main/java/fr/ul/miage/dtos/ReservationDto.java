package fr.ul.miage.dtos;

import fr.ul.miage.Enums.ETypeImmat;
import fr.ul.miage.Enums.ETypeReservation;

import java.time.LocalDateTime;

/**
 * Objet représentant une réservation pour une borne.
 */
public class ReservationDto {
    /**
     * La plaque d'immatriculation du véhicule associé à la réservation.
     */
    private String plaque;

    /**
     * L'identifiant de la borne associée à la réservation.
     */
    private int idBorne;

    /**
     * L'heure de début de la réservation.
     */
    private LocalDateTime heureDebut;

    /**
     * L'heure de fin de la réservation.
     */
    private LocalDateTime heureFin;

    /**
     * Indique si la réservation est unique ou garantie (c'est à dire qu'elle se répète chaque semaine)
     */
    private ETypeReservation typeReservation;

    /**
     * Le nombre de prolongations qu'il y a sur la réservation. Pour le moment, toujours 0 car non traité.
     */
    private int nbProlongations;

    /**
     * L'id de la réservation
     */
    private int idReservation;

    /**
     * Constructeur d'une réservation.
     * @param plaque la plaque d'immatriculation du véhicule associé à la réservation.
     * @param idBorne l'identifiant de la borne associée à la réservation.
     * @param heureDebut l'heure de début de la réservation.
     * @param heureFin l'heure de fin de la réservation.
     * @param typeRes le type de la plaque de réservation (unique ou garantie).
     * @param nbProlongations le nombre de prolongations (non traité actuellement).
     * @param idres l'id de la réservation
     */
    public ReservationDto(String plaque,
                          int idBorne,
                          LocalDateTime heureDebut,
                          LocalDateTime heureFin,
                          ETypeReservation typeRes,
                          int nbProlongations,
                          int idres) {
        this.plaque = plaque;
        this.idBorne = idBorne;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.typeReservation = typeRes;
        this.nbProlongations = nbProlongations;
        this.idReservation = idres;
    }

    // Getters

    public int getIdBorne() {
        return idBorne;
    }

    public LocalDateTime getHeureDebut() {
        return heureDebut;
    }

    public LocalDateTime getHeureFin() {
        return heureFin;
    }

    public ETypeReservation getTypeReservation() {
        return typeReservation;
    }

    public int getNbProlongations() {
        return nbProlongations;
    }

    public int getIdReservation() {
        return idReservation;
    }
}
