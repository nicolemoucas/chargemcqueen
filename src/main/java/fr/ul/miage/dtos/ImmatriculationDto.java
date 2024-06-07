package fr.ul.miage.dtos;

import fr.ul.miage.Enums.ETypeImmat;

/**
 * Objet représentant une plaque d'immatriculation.
 */
public class ImmatriculationDto {
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

    // Getters
    public String getPlaque() {
        return plaque;
    }

    public ETypeImmat getTypeImmat() {
        return typeImmat;
    }
}
