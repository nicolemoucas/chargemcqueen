package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;
import fr.ul.miage.dtos.CompteClientDto;
import fr.ul.miage.dtos.MotDePasseDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OutilsCompte {
    /**
     * Redirige vers l'action choisie par l'utilisateur en fonction du numéro d'ordre passé en paramètre
     * pour le menu quand le client est connecté à son compte.
     *
     * @param ordreUtilisateur Instruction (int) choisie par l'utilisateur
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

    protected static CompteClientDto getCompteClientBDD(String mail) {
        ResultSet result = null;
        PreparedStatement stmt = null;

        String query = "SELECT cl.idclient, motdepasse, sel FROM Client cl LEFT JOIN Compte co \n" +
                "\tON cl.idclient = co.idclient WHERE LOWER(cl.email) LIKE LOWER(?);";
        try {
            stmt = OutilsBaseSQL.getConn().prepareStatement(query);
            stmt.setString(1, mail);

            result = stmt.executeQuery();
            if (result.next()) {
                int idclient = result.getInt("idclient");
                MotDePasseDto mdpDto = new MotDePasseDto(result.getString("motdepasse"),
                        result.getString("sel"));
                return new CompteClientDto(idclient, mdpDto);
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la connexion, veuillez réessayer.");
            throw new RuntimeException(e);
        }
        return null;
    }


    private static boolean deconnexion() {
        System.out.println("Se déconnecter");
        return true; // stopApp
    }

    private static List<String> chargerOptionsMenuCompte() {
        List<String> options = new ArrayList<String>(Arrays.asList("Trouver ma réservation",
                "Faire une réservation", "Ajouter un véhicule", "Se déconnecter"));
        if (BorneMere.getEstAdmin()) {
            options.add("Rechercher un client");
        }
        return options;
    }

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

    protected static CompteClientDto creerCompteClient(int idClient){
        Scanner scanner = new Scanner(System.in);
        String motDePasse = FormulaireInscriptionClient.recupererMotDePasse(scanner);
        MotDePasseDto motDePasseChiffre = Outils.hashPassword(motDePasse);
        return new CompteClientDto(idClient, motDePasseChiffre);
    }

    public static void insererCompteClientBDD(CompteClientDto compteClient) {
        ResultSet resultIdClient = null;
        String query = "INSERT INTO Compte (idClient, motDePasse, sel) VALUES (?, ?, ?)" ;
        PreparedStatement stmt = null;
        try {
            stmt = OutilsBaseSQL.getConn().prepareStatement(query);
            stmt.setInt(1, compteClient.getIdClient());
            stmt.setString(2, compteClient.getMotDePasse().getMotDePasseChiffre());
            stmt.setString(3, compteClient.getMotDePasse().getSel());
            int lignesAffectees = stmt.executeUpdate();
            if (lignesAffectees == 0) {
                System.out.println("Une erreur s'est produite lors de l'insertion du compte client.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de l'insertion du compte client.");
            throw new RuntimeException(e);
        }
    }
}
