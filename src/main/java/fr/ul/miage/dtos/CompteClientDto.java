package fr.ul.miage.dtos;

/**
 * Objet représentant le compte d'un client?
 */
public class CompteClientDto {
    /**
     * L'id du client a qui appartient le compte.
     */
    String idClient;

    /**
     * Le mot de passe du client. Contient le mot de passe chiffré et le sel.
     */
    MotDePasseDto motDePasse;

    /**
     * Constructeur de CompteClientDto
     * @param idClient l'id du client a qui appartient le compte.
     * @param motDePasse le mot de passe du client.
     */
    public CompteClientDto(String idClient, MotDePasseDto motDePasse) {
        this.idClient = idClient;
        this.motDePasse = motDePasse;
    }

// Getters
    public MotDePasseDto getMotDePasse() {
        return motDePasse;
    }
}
