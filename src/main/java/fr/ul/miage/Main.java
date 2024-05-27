package fr.ul.miage;

import java.sql.*;
import java.util.*;

/**
 * Classe principale de l'application Charge McQueen.
 */
public class Main {
    private static List<String> optionsMenu = new ArrayList<>();

    /**
     * Méthode principale de l'application qui permet de la lancer et de gérer le flux de l'utilisateur.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Charger le pilote JDBC PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Créer une nouvelle connexion à la base de données PostgreSQL
            // Il faudrat penser à éxécuter le script de création de BDD avant
            String url = "jdbc:postgresql://localhost:5433/db_charge_mcqueen";
            String user = "postgres";
            String password = "postgres";
            conn = DriverManager.getConnection(url, user, password);

            // Vérifier que la connexion a réussi
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie !");

                bienvenue();
                afficherMenu();

                // Créer une nouvelle instruction SQL
              /**
                stmt = conn.createStatement();

                // Exécuter une requête SQL pour récupérer des données à partir d'une table
                String sql = "SELECT * FROM Client";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println(rs.getString("nom"));
                }
                **/


            }
        } catch (ClassNotFoundException e) {
            // Le pilote JDBC n'a pas été trouvé dans le chemin de classe
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            // Une erreur s'est produite lors de la connexion à la base de données
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    // Fermer la connexion à la base de données
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        int ordreUtilisateur = -1;

        optionsMenu = chargerOptionsMenu();
        bienvenue();
        while (ordreUtilisateur != 0) {
            ordreUtilisateur = saisirChoixMenu();
            executerOrdre(ordreUtilisateur);
        }
        auRevoir();
    }

    /**
     * Créé une liste avec les options du menu principal de l'application.
     *
     * @return Liste des options disponibles
     */
    private static List<String> chargerOptionsMenu() {
        return new ArrayList<String>(Arrays.asList("Trouver ma réservation",
                "Saisir ma plaque d'immatriculation", "S'inscrire", "Se connecter"));
    }

    /**
     * Affiche un message de bienvenue à l'utilisateur.
     */
    private static void bienvenue() {
        System.out.println("Bienvenue chez Charge McQueen !");
        System.out.println(
                "           .--------.\n" +
                        "      ____/_____|___ \\___\n" +
                        "     O    _   - |   _   ,*\n" +
                        "      '--(_)-------(_)--'  \n\n");
    }

    /**
     * Affiche un message d'au revoir à l'utilisateur.
     */
    private static void auRevoir() {
        System.out.println("\nÀ bientôt chez Charge McQueen⚡️ !");
        System.out.println(
                "          ______\n" +
                "  *-- *- /|_||_\\`.__\n" +
                "*-- *-  (   _    _ _\\\n" +
                "  *- *--`-(_)--(_)-'");
    }

    /**
     * Affiche les options du menu à l'utilisateur.
     */
    private static void afficherMenu() {
        for (int i = 0; i < optionsMenu.size(); i++) {
            System.out.println((i+1) + ". " + optionsMenu.get(i));
        }
    }

    /**
     * Demande à l'utilisateur de saisir un choix à partir du menu.
     *
     * @return choix (int) saisi par l'utilisateur
     */
    protected static int saisirChoixMenu() {
        Scanner scanner = new Scanner(System.in);
        int choixSaisi = -1;
        while (choixSaisi < 0 || choixSaisi > optionsMenu.size()) {
            try {
                System.out.println("\nChoissisez une option parmi les suivantes (ex : 1) :");
                afficherMenu();
                System.out.println("0.\t   __\n" +
                        "\t /    \\\n" +
                        "\t| STOP |\n" +
                        "\t \\ __ /\n" +
                        "\t   ||\n" +
                        "\t ~~~~~~");
                System.out.print("\nQuel est votre choix ? : ");
                choixSaisi = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("/!\\ Erreur : veuillez saisir un nombre entre 0 et " + optionsMenu.size());
                scanner.nextLine();
            }
        }
        return choixSaisi;
    }

    /**
     * Redirige vers l'action choisie par l'utilisateur en fonction du numéro d'ordre passé en paramètre.
     *
     * @param ordreUtilisateur Instruction (int) choisie par l'utilisateur
     */
    private static void executerOrdre(int ordreUtilisateur) {
        switch (ordreUtilisateur) {
            case 1:
                chercherReservation();
                break;
            case 2:
                saisirPlaque();
                break;
            case 3:
                inscription();
                break;
            case 4:
                connexion();
                break;
            default:
                break;
        }
    }

    private static void chercherReservation() {
        System.out.println("Chercher une réservation");
    }

    private static void saisirPlaque() {
        System.out.println("Saisir une plaque d'immatriculation");
    }

    private static void connexion() {
        System.out.println("Se connecter à un compte");
    }

    private static void inscription() {
        System.out.println("S'inscrire");
    }
}