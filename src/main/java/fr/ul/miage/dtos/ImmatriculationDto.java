package fr.ul.miage.dtos;

import fr.ul.miage.Enums.ETypeImmat;

public class ImmatriculationDto {
    private int idPlaque = -2;
    private String plaque;
    private ETypeImmat typeImmat;

    public ImmatriculationDto(String plaque, ETypeImmat typeImmat) {
        this.plaque = plaque;
        this.typeImmat = typeImmat;
    }

    public ImmatriculationDto(int idPlaque, String plaque, ETypeImmat typeImmat) {
        this.idPlaque = idPlaque;
        this.plaque = plaque;
        this.typeImmat = typeImmat;
    }

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
