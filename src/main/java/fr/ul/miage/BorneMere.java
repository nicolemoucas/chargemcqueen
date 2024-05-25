package fr.ul.miage;

import java.util.*;

/**
 * Classe principale de l'application Charge McQueen.
 */
public class BorneMere {
    protected static List<String> optionsMenuInitial = new ArrayList<>();
    private static final boolean estAdmin = true;

    /**
     * Méthode principale de l'application qui permet de la lancer et de gérer le flux de l'utilisateur.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {

        optionsMenuInitial = chargerOptionsMenuInitial();
        bienvenue();
        System.out.println("\nMenu principal");
        runMenuLoop(optionsMenuInitial, "menuPrincipal");
        auRevoir();
    }

    public static boolean getEstAdmin() {
        return estAdmin;
    }

    private static void runMenuLoop(List<String> optionsMenu, String typeMenu) {
        int ordreUtilisateur = -1;
        boolean stopApp = false;

        while (ordreUtilisateur != 0 && !stopApp) {
            ordreUtilisateur = saisirChoixMenu(optionsMenu);
            switch (typeMenu) {
                case "menuPrincipal":
                    stopApp = executerOrdreMenuPrincipal(ordreUtilisateur);
                    break;
                case "menuCompte":
                    stopApp = OutilsCompteClient.executerOrdreMenuCompte(ordreUtilisateur);
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
        System.out.println("Bienvenue chez Charge McQueen !");
        System.out.println(
                """
                                   .--------.
                              ____/_____|___ \\___
                             O    _   - |   _   ,*
                              '--(_)-------(_)--' \s

                        """);
    }

    /**
     * Affiche un message d'au revoir à l'utilisateur.
     */
    private static void auRevoir() {
        System.out.println("\nÀ bientôt chez Charge McQueen ! ⚡️");
        System.out.println(
                """
                                  ______
                          *-- *- /|_||_\\`.__
                        *-- *-  (   _    _ _\\
                          *- *--`-(_)--(_)-'""");
    }

    /**
     * Affiche les options d'un menu à l'utilisateur.
     */
    private static void afficherMenu(List<String> optionsMenu) {
        for (int i = 1; i <= optionsMenu.size(); i++) {
            System.out.println(i + ". " + optionsMenu.get(i-1));
        }
    }

    /**
     * Demande à l'utilisateur de saisir un choix à partir d'une liste d'options.
     *
     * @return choix (int) saisi par l'utilisateur
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
                connexion();
                break;
            case 4:
                inscription();
                break;
            case 5:
                return true; // stopApp
            default:
                return false;
        }
        return false;
    }

    protected static void ajouterPlaque() {
        System.out.println("Ajouter un véhicule");
    }

    protected static void faireReservation() {
        System.out.println("Faire une réservation");
    }

    protected static void chercherReservation() {
        System.out.println("Chercher une réservation");
    }

    private static void saisirPlaque() {
        System.out.println("Saisir une plaque d'immatriculation");
    }

    private static void connexion() {
        List<String> optionsMenuCompte = chargerOptionsMenuCompte();
        System.out.println("Vous êtes connecté à votre compte");
        runMenuLoop(optionsMenuCompte, "menuCompte");
    }

    private static List<String> chargerOptionsMenuCompte() {
        List<String> options = new ArrayList<String>(Arrays.asList("Trouver ma réservation",
                "Faire une réservation", "Ajouter un véhicule", "Se déconnecter"));
        if (BorneMere.estAdmin) {
            options.add("Rechercher un client");
        }
        return options;
    }

    private static void inscription() {
        new FormulaireInscriptionClient().procedureInscription();
    }
}