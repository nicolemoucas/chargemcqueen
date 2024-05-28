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

    protected static void connexion() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> optionsMenuCompte = chargerOptionsMenuCompte();
        String mail = FormulaireInscriptionClient.recupererMail(scanner);
        String mdp = FormulaireInscriptionClient.recupererMotDePasse(scanner);

        Statement stmt = OutilsBaseSQL.getConn().createStatement();
        ResultSet result = null;

        // Exécuter une requête SQL pour récupérer des données à partir d'une table
        String query = "SELECT prenom FROM Client WHERE ";
        result = stmt.executeQuery(query);
        while (result.next()) {
            System.out.println(result.getString("nom"));
        }

        System.out.println("Bienvenue [nom] !");
        BorneMere.runMenuLoop(optionsMenuCompte, "menuCompte");
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

    protected static int insererClientBDD(ClientDto client) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ResultSet resultIdClient = null;
        String query = "INSERT INTO Client (nom, prenom, adresse, numTelephone, numCarte) VALUES (?, ?, ?, ?, ?)" ;
        PreparedStatement stmt = OutilsBaseSQL.getConn().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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
        System.out.println("Une erreur s'est produite lors de l'insertion du client.");
        return 0;
    }

    protected static CompteClientDto creerCompteClient(int idClient){
        Scanner scanner = new Scanner(System.in);
        String motDePasse = FormulaireInscriptionClient.recupererMotDePasse(scanner);
        byte[] selBytes = Outils.generateSalt(16);
        byte[] hashedPasswordBytes = Outils.hashPassword(motDePasse.toCharArray(), selBytes);
        //TODO : Quand il y aura la BDD, il faut récupérer l'ID du client pour lui lier le compte.
        return new CompteClientDto("1",
                new MotDePasseDto(
                        Outils.convertByteArrayToString(hashedPasswordBytes),
                        Outils.convertByteArrayToString(selBytes)));
    }
}
