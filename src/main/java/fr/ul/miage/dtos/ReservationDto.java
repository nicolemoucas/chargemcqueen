package fr.ul.miage.dtos;

import fr.ul.miage.Enums.ETypeImmat;

import java.time.LocalDateTime;

public class ReservationDto {
    private int idImmat;
    private int idBorne;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private ETypeImmat typeImmat;
    private int nbProlongations;

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

    public int getIdImmat() {
        return idImmat;
    }

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
