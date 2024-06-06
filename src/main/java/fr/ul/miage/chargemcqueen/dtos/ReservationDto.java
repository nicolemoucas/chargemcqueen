package fr.ul.miage.chargemcqueen.dtos;

import fr.ul.miage.chargemcqueen.Enums.ETypeImmat;

import java.time.LocalDateTime;

/**
 * Objet représentant une réservation pour une borne.
 */
public class ReservationDto {
    /**
     * L'identifiant de l'immatriculation du véhicule associé à la réservation.
     */
    private int idImmat;

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
     * Indique si la plaque est permanante (normale) ou temporaire (dans le cadre des locations).
     */
    private ETypeImmat typeImmat;

    /**
     * Le nombre de prolongations qu'il y a sur la réservation. Pour le moment, toujours 0 car non traité.
     */
    private int nbProlongations;

    /**
     * Constructeur d'une réservation.
     * @param idImmat l'identifiant de la plaque d'immatriculation du véhicule associé à la réservation.
     * @param idBorne l'identifiant de la borne associée à la réservation.
     * @param heureDebut l'heure de début de la réservation.
     * @param heureFin l'heure de fin de la réservation.
     * @param typeImmat le type de la plaque d'immatriculation (normale ou temporaire).
     * @param nbProlongations le nombre de prolongations (non traité actuellement).
     */
    public ReservationDto(int idImmat,
                          int idBorne,
                          LocalDateTime heureDebut,
                          LocalDateTime heureFin,
                          ETypeImmat typeImmat,
                          int nbProlongations) {
        this.idImmat = idImmat;
        this.idBorne = idBorne;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.typeImmat = typeImmat;
        this.nbProlongations = nbProlongations;
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

    public ETypeImmat getTypeImmat() {
        return typeImmat;
    }

    public int getNbProlongations() {
        return nbProlongations;
    }
}
