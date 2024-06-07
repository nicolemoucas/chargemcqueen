package fr.ul.miage.Enums;

public enum ETypeReservation {

    // Liste des valeurs possibles

    UNIQUE("Unique"),
    GARANTIE("Garantie");

    private final String value;

    ETypeReservation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
