package fr.ul.miage.Enums;

public enum ETypeImmat {

    // Liste des valeurs possibles
    TEMPORAIRE("Temporaire"),
    NORMALE("Normale");

    private final String value;

    ETypeImmat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
