package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;
import fr.ul.miage.dtos.ReservationDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe principale de l'application Charge McQueen.
 */
public class BorneMere {
    protected static List<String> optionsMenuInitial = new ArrayList<>();
    private static OutilsBaseSQL outilsBaseSQL;

    /**
     * Si un client est connecté, il est stocké ici. A sa déconnexion, il repassera à null.
     */
    private static ClientDto currentlyConnectedClient = null;

    /**
     * Méthode principale de l'application qui permet de la lancer et de gérer le flux de l'utilisateur.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        outilsBaseSQL = OutilsBaseSQL.getInstance();
        outilsBaseSQL.makeConnexion();
        optionsMenuInitial = chargerOptionsMenuInitial();
        bienvenue();
        System.out.println("\nMenu principal");
        runMenuLoop(optionsMenuInitial, "menuPrincipal");
        auRevoir();
        outilsBaseSQL.fermerConnexion();
    }

    /**
     * Exécute une boucle pour afficher un menu et attendre une saisie de l'utilisateur
     * jusqu'à ce qu'il choisisse de quitter l'application.
     *
     * @param optionsMenu La liste des options à afficher dans le menu
     * @param typeMenu Le type de menu à afficher ("menuPrincipal", "menuCompte")
     */
    protected static void runMenuLoop(List<String> optionsMenu, String typeMenu) {
        int ordreUtilisateur = -1;
        boolean stopLoop = false;

        while (ordreUtilisateur != 0 && !stopLoop) {
            ordreUtilisateur = saisirChoixMenu(optionsMenu);
            switch (typeMenu) {
                case "menuPrincipal":
                    // false quand l'utilisateur choisit "STOP" (l'application)
                    stopLoop = executerOrdreMenuPrincipal(ordreUtilisateur);
                    break;
                case "menuCompte":
                    // false quand l'utilisateur se déconnecte
                    stopLoop = OutilsCompte.executerOrdreMenuCompte(ordreUtilisateur);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Créé une liste avec les options du menu principal de l'application.
     *
     * @return Liste des options disponibles
     */
    private static List<String> chargerOptionsMenuInitial() {
        return new ArrayList<String>(Arrays.asList("Trouver ma réservation",
                "Saisir ma plaque d'immatriculation", "Se connecter", "S'inscrire",
                """
                         __
                     \t /    \\
                     \t| STOP |
                     \t \\ __ /
                     \t   ||
                     \t ~~~~~~"""));
    }

    /**
     * Affiche un message de bienvenue à l'utilisateur.
     */
    private static void bienvenue() {
        System.out.println("Bienvenue chez Charge McQueen ! ⚡️");
        System.out.println(
                """
                                  ______
                          *-- *- /|_||_\\`.__
                        *-- *-  (   _    _ _\\
                          *- *--`-(_)--(_)-'""");
    }

    /**
     * Affiche un message d'au revoir à l'utilisateur.
     */
    private static void auRevoir() {
        System.out.println("\nÀ bientôt chez Charge McQueen ! ⚡️");
        System.out.println("""
                   _                      _                          ⚡️\s
                  | |__   __ _     __    | |_      ___   __ __ __     ⚡️
                  | / /  / _` |   / _|   | ' \\    / _ \\  \\ V  V /      ⚡️
                  |_\\_\\  \\__,_|   \\__|_  |_||_|   \\___/   \\_/\\_/        ⚡️\s
                 |""\"""|_|""\"""|_|""\"""|_|""\"""|_|""\"""|_|""\"""|         ⚡️\s
                 `-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'          ⚡️\s""");
    }

    /**
     * Affiche les options d'un menu (liste) à l'utilisateur.
     *
     * @param optionsMenu La liste des options à afficher
     */
    private static void afficherMenu(List<String> optionsMenu) {
        for (int i = 1; i <= optionsMenu.size(); i++) {
            System.out.println(i + ". " + optionsMenu.get(i-1));
        }
    }

    /**
     * Demande à l'utilisateur de saisir un choix à partir d'une liste d'options.
     *
     * @param optionsMenu La liste des options à afficher
     * @return Choix (int) saisi par l'utilisateur
     */
    protected static int saisirChoixMenu(List<String> optionsMenu) {
        System.out.println("\nChoissisez une option parmi les suivantes (ex : 1) :");
        afficherMenu(optionsMenu);
        int choixSaisi = Outils.saisirInt(0, optionsMenu.size());
        return choixSaisi;
    }

    /**
     * Redirige vers l'action choisie par l'utilisateur en fonction du numéro d'ordre passé en paramètre
     * pour le menu principal de l'application.
     *
     * @param ordreUtilisateur Instruction (int) choisie par l'utilisateur
     * @return true si l'application doit s'arrêter, false sinon
     */
    private static boolean executerOrdreMenuPrincipal(int ordreUtilisateur) {
        switch (ordreUtilisateur) {
            case 1:
                chercherReservation();
                break;
            case 2:
                saisirPlaque();
                break;
            case 3:
                OutilsCompte.connexion();
                break;
            case 4:
                OutilsCompte.inscription();
                break;
            case 5:
                return true; // arrêter l'application
            default:
                return false;
        }
        return false;
    }

    /**
     * TODO : Permet à l'utilisateur d'ajouter un véhicule à son compte.
     */
    protected static void ajouterPlaque() {
        System.out.println("Ajouter un véhicule");
    }

    /**
     * TODO : Permet à l'utilisateur de faire une réservation.
     */
    protected static void faireReservation() {
        ReservationDto reservationDto = new ReservationClientInscrit().procedureReservation();
    }

    /**
     * TODO : Permet à l'utilisateur de chercher une réservation à partir du numéro de réservation.
     */
    protected static void chercherReservation() {
        System.out.println("Chercher une réservation");
    }

    /**
     * TODO : Permet à l'utilisateur de saisir une plaque d'immatriculation pour chercher les
     * réservations associées.
     */
    private static void saisirPlaque() {
        System.out.println("Saisir une plaque d'immatriculation");
    }

    /**
     * Renvoie le client actuellement connecté.
     *
     * @return L'objet ClientDto du client actuellement connecté ou null s'il n'y en a pas.
     */
    // Getters et Setters
    public static ClientDto getCurrentlyConnectedClient() {
        return currentlyConnectedClient;
    }

    /**
     * Définir le client actuellement connecté.
     *
     * @param currentlyConnectedClient L'objet ClientDto qui représente le client à connecter ou
     *                                 null si le client se déconnecte
     */
    public static void setCurrentlyConnectedClient(ClientDto currentlyConnectedClient) {
        BorneMere.currentlyConnectedClient = currentlyConnectedClient;
    }

    /**
     * La méthode revoie les bornes disponibles à l'instant présent
     *
     * @return listes des bornes disponibles
     */
    private ResultSet getBornesDisponibles() {
        ResultSet resultIdBornes = null;
        try {
            String query = "Select * \n" +
                    "from bornerecharge br\n" +
                    "left join reservation res\n" +
                    "on br.idborne = res.idborne\n" +
                    "where br.etatBorne = 'disponible';" ;
            Statement stmt = outilsBaseSQL.getConn().createStatement();
            resultIdBornes = stmt.executeQuery(query);
        } catch (SQLException e){
            System.out.println("Une erreur s'est produite lors de la recherche des bornes disponibles !");
        } finally {
            return resultIdBornes;
        }
    }
    // End Getters et Setters
}