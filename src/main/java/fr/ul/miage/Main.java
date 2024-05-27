package fr.ul.miage;

import java.sql.*;

public class Main {
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

                // Créer une nouvelle instruction SQL
                stmt = conn.createStatement();

                // Exécuter une requête SQL pour récupérer des données à partir d'une table
                String sql = "SELECT * FROM Client";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println(rs.getString("nom"));
                }


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
    }

    private static void bienvenue() {
        System.out.println("Bienvenue chez Charge McQueen !");
        System.out.println(
                "           .--------.\n" +
                "      ____/_____|___ \\___\n" +
                "     O    _   - |   _   ,*\n" +
                "      '--(_)-------(_)--'  ");
    }

    private static void afficherMenu() {
        System.out.println();
    }
}