package fr.ul.miage.dtos;

/**
 * Objet représentant un client.
 */
public class ClientDto {

    /**
     * L'id du client en BDD
     */
    int idClient;

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

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteBancaire() {
        return carteBancaire;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    // Fin Getters et Setters
}
