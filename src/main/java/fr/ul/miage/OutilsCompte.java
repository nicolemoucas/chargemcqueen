package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;
import fr.ul.miage.dtos.CompteClientDto;
import fr.ul.miage.dtos.MotDePasseDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Cette classe est une boite à outils, et contient des méthodes versatiles pour le compte client.
 */
public class OutilsCompte {
    /**
     * Redirige vers l'action choisie par l'utilisateur en fonction du numéro d'ordre passé en paramètre
     * pour le menu quand le client est connecté à son compte.
     *
     * @param ordreUtilisateur L'instruction (int) choisie par l'utilisateur
     * @return Un booléen qui indique si l'utilisateur a choisi de se déconnecter
     */
    protected static boolean executerOrdreMenuCompte(int ordreUtilisateur) {
        switch (ordreUtilisateur) {
            case 1:
                BorneMere.chercherReservation();
                break;
            case 2:
                BorneMere.faireReservation();
                break;
            case 3:
                BorneMere.ajouterPlaque();
                break;
            case 4:
                return deconnexion();
            case 5:
                OutilsCompteAdmin.chercherClient();
            default:
                break;
        }
        return false;
    }

    /**
     * Méthode qui gère le processus d'inscription d'un nouveau client.
     */
    protected static void inscription() {
        ClientDto client = new FormulaireInscriptionClient().procedureInscription();
        int idClient = OutilsCompte.insererClientBDD(client);
        if (idClient > 0) {
            CompteClientDto compteClient = creerCompteClient(idClient);
            OutilsCompte.insererCompteClientBDD(compteClient);
            System.out.println("\nVotre compte a été créé. Bienvenue à vous, " + client.getPrenom()+ " !" +
                    "\nVeuillez vous connecter.");
        } else {
            System.out.println("\nUne erreur s'est produite lors de l'inscription. Veuillez réessayer");
        }
    }

    /**
     * Méthode qui gère le processus de connexion d'un client.
     */
    protected static void connexion() {
        boolean connexionOK = new ConnexionCompteClient().procedureConnexionCompte();
        List<String> optionsMenuCompte = chargerOptionsMenuCompte();

        if (connexionOK) {
            System.out.println("\nBienvenue !");
            BorneMere.runMenuLoop(optionsMenuCompte, "menuCompte");
        } else {
            System.out.println("La connexion a échoué.\nVous serez redirigé vers le menu principal. ");
        }
    }

    /**
     * Méthode qui récupère le compte client d'un utilisateur de la base de données à partir de son
     * adresse email.
     *
     * @param mail L'adresse email de l'utilisateur
     * @return Le compte client de l'utilisateur ou null s'il n'y a aucun compte avec ce mail
     */
    protected static CompteClientDto getCompteClientBDD(String mail) {
        ResultSet result = null;

        String query = "SELECT cl.idclient, motdepasse, sel FROM Client cl LEFT JOIN Compte co \n" +
                "\tON cl.idclient = co.idclient WHERE LOWER(cl.email) LIKE LOWER(" + mail + ");";

        String erreur = "Une erreur s'est produite lors de la connexion, veuillez réessayer.";

        result = OutilsBaseSQL.rechercheSQL(query, erreur);

        try {
            if (result.next()) {
                int idclient = result.getInt("idclient");
                MotDePasseDto mdpDto = new MotDePasseDto(result.getString("motdepasse"),
                        result.getString("sel"));
                return new CompteClientDto(idclient, mdpDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(erreur);
        }

        return null;
    }


    /**
     * Méthode qui gère la déconnexion de l'utilisateur.
     *
     * @return Un booléen qui indique si l'utilisateur a choisi de se déconnecter
     */
    private static boolean deconnexion() {
        System.out.println("Se déconnecter");
        return true; // stopApp
    }

    /**
     * Méthode qui charge les options du menu compte.
     *
     * @return Une liste d'options pour le menu compte
     */
    private static List<String> chargerOptionsMenuCompte() {
        List<String> options = new ArrayList<String>(Arrays.asList("Trouver ma réservation",
                "Faire une réservation", "Ajouter un véhicule", "Se déconnecter"));
        if (BorneMere.getEstAdmin()) {
            options.add("Rechercher un client");
        }
        return options;
    }

    /**
     * Méthode qui insère un nouveau client dans la base de données.
     *
     * @param client Le client à insérer
     * @return L'identifiant du client inséré, ou -1 s'il y a une erreur
     */
    protected static int insererClientBDD(ClientDto client) {
        Scanner scanner = new Scanner(System.in);
        ResultSet resultIdClient = null;
        if (getCompteClientBDD(client.getEmail()) != null) {
            System.out.println("Cet email est déjà utilisé par un autre client.\nVeuillez utiliser un email différent.");
            return -1;
        }

        String query = "INSERT INTO Client (nom, prenom, email, numTelephone, numCarte) VALUES (?, ?, ?, ?, ?)" ;
        PreparedStatement stmt = null;
        try {
            stmt = OutilsBaseSQL.getConn().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());
            stmt.setString(5, client.getCarteBancaire());

            int lignesAffectees = stmt.executeUpdate();
            if (lignesAffectees == 1) {
                resultIdClient = stmt.getGeneratedKeys();
                if (resultIdClient.next()) {
                    return resultIdClient.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Une erreur s'est produite lors de l'insertion du client.");
        return -1;
    }

    /**
     * Méthode qui crée un nouveau compte client à partir de l'identifiant d'un client.
     *
     * @param idClient L'identifiant du client
     * @return Le compte client créé
     */
    protected static CompteClientDto creerCompteClient(int idClient){
        Scanner scanner = new Scanner(System.in);
        String motDePasse = FormulaireInscriptionClient.recupererMotDePasse(scanner);
        MotDePasseDto motDePasseChiffre = Outils.hashPassword(motDePasse);
        return new CompteClientDto(idClient, motDePasseChiffre);
    }

    /**
     * Méthode qui insère un nouveau compte client dans la base de données.
     *
     * @param compteClient Le compte client à insérer
     */
    public static void insererCompteClientBDD(CompteClientDto compteClient) {
        String query = "INSERT INTO Compte (idClient, motDePasse, sel) VALUES (" + compteClient.getIdClient() + ", " + compteClient.getMotDePasse().getMotDePasseChiffre() + ", " + compteClient.getMotDePasse().getSel() + ")";
        boolean resultat = OutilsBaseSQL.majSQL(query, "Une erreur s'est produite lors de l'insertion du compte client.");
        if (resultat) {
            System.out.println("Une erreur s'est produite lors de l'insertion du compte client.");
        }
    }
}
