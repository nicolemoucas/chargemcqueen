package fr.ul.miage.dtos;

public class ClientDto {
    /**
     * Le nom du client.
     */
    String nom;

    /**
     * Le prénom du client.
     */
    String prenom;

    /**
     * Le numéro de téléphone du client.
     */
    String telephone;

    /**
     * L'adresse mail du client.
     */
    String email;

    /**
     * Le numéro de carte bancaire du client.
     */
    String carteBancaire;

    /**
     * Constructeur de l'objet ClientDto.
     *
     * @param nom nom du client.
     * @param prenom prénom du client.
     * @param telephone numéro de téléphone du client.
     * @param email adresse mail du client.
     * @param carteBancaire numéro de carte bancaire du client.
     */
    public ClientDto(String nom, String prenom, String telephone, String email, String carteBancaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.carteBancaire = carteBancaire;
    }
}
