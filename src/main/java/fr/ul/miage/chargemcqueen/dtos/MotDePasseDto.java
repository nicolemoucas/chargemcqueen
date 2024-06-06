package fr.ul.miage.chargemcqueen.dtos;

/**
 * Objet représentant le mot de passe d'un client.
 */
public class MotDePasseDto {
    /**
     * Le mot de passé chiffré en hexadecimal.
     */
    String motDePasseChiffre;

    /**
     * Le sel utilisé pour chiffrer le mot de passe (en hexadecimal).
     */
    String sel;

    @Override
    public String toString() {
        return "MotDePasseDto{" +
                "motDePasseChiffre='" + motDePasseChiffre + '\'' +
                ", \nsel='" + sel + '\'' +
                '}';
    }

    /**
     * Constructeur de MotDePasseDto.
     * @param motDePasseChiffre le mot de passe chiffré en hexadécimal.
     * @param sel le sel en héxadécimal.
     */
    public MotDePasseDto(String motDePasseChiffre, String sel) {
        this.motDePasseChiffre = motDePasseChiffre;
        this.sel = sel;
    }

    // Getters
    public String getMotDePasseChiffre() {
        return motDePasseChiffre;
    }

    public String getSel() {
        return sel;
    }
}
