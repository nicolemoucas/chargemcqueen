package fr.ul.miage.dtos;

public class CompteClientDto {

    /**
     * L'id du ClientDto auquel le compte est lié
     */
    int idClient;

    /**
     * Ce mot de passe est chiffré et stocké avec son sel pour les vérifications lors de la connexion.
     */
    MotDePasseDto motDePasse;

    /**
     * Constructeur du compte client.
     * @param idClient l'id du client auquel le compte est lié.
     * @param motDePasse le mot de passe chiffré et son sel.
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
}
