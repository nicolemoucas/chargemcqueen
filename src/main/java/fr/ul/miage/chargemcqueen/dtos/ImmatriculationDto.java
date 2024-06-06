package fr.ul.miage.chargemcqueen.dtos;

import fr.ul.miage.chargemcqueen.Enums.ETypeImmat;

/**
 * Objet représentant une plaque d'immatriculation.
 */
public class ImmatriculationDto {
    /**
     * L'identifiant de la plaque. -2 si elle n'est pas encore enregistrée en base.
     */
    private int idPlaque = -2;

    /**
     * Le texte de la plaque. Forcément de la forme AA111BB.
     */
    private String plaque;

    /**
     * Indique si la plaque est permanante (normale) ou temporaire (dans le cadre des locations).
     */
    private ETypeImmat typeImmat;

    /**
     * Constructuer de l'objet plaque d'immatriculation.
     *
     * @param plaque la plaque d'immatriculation de la forme AA111BB.
     * @param typeImmat le type de la plaque d'immatriculation (normale ou temporaire).
     */
    public ImmatriculationDto(String plaque, ETypeImmat typeImmat) {
        this.plaque = plaque;
        this.typeImmat = typeImmat;
    }

    /**
     * Constructuer de l'objet plaque d'immatriculation.
     *
     * @param idPlaque l'identifiant de la plaque d'immatriculation.
     * @param plaque la plaque d'immatriculation de la forme AA111BB.
     * @param typeImmat le type de la plaque d'immatriculation (normale ou temporaire).
     */
    public ImmatriculationDto(int idPlaque, String plaque, ETypeImmat typeImmat) {
        this.idPlaque = idPlaque;
        this.plaque = plaque;
        this.typeImmat = typeImmat;
    }

    // Getters
    public int getIdPlaque() {
        return idPlaque;
    }

    public String getPlaque() {
        return plaque;
    }

    public ETypeImmat getTypeImmat() {
        return typeImmat;
    }
}
