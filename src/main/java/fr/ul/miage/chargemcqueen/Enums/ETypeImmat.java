package fr.ul.miage.chargemcqueen.Enums;

public enum ETypeImmat {
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
