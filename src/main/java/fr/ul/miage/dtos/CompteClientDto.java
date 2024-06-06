package fr.ul.miage.dtos;

/**
 * Objet représentant le compte d'un client
 */
public class CompteClientDto {

    /**
     * L'id du client a qui appartient le compte.
     */
    int idClient;

    /**
     * Le mot de passe du client. Contient le mot de passe chiffré et le sel.
     */
    MotDePasseDto motDePasse;

    /**
     * Constructeur de CompteClientDto
     *
     * @param idClient l'id du client à qui appartient le compte.
     * @param motDePasse le mot de passe du client.
     */
    public CompteClientDto(int idClient, MotDePasseDto motDePasse) {
        this.idClient = idClient;
        this.motDePasse = motDePasse;
    }

    // Getters et Setters
    public int getIdClient() {
        return idClient;
    }

    public MotDePasseDto getMotDePasse() {
        return motDePasse;
    }
    // End Getters et Setters


    @Override
    public String toString() {
        return "CompteClientDto{" +
                "idClient=" + idClient +
                ", motDePasse=" + motDePasse +
                '}';
    }
}
