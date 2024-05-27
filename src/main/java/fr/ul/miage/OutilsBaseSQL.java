package fr.ul.miage;

import java.sql.*;

public class OutilsBaseSQL {
    private Connection conn = null;

    public void connexionBase() {
        try {
            // Charger le pilote JDBC PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Créer une nouvelle connexion à la base de données PostgreSQL
            // Il faudrait penser à exécuter le script de création de BDD avant
            String url = "jdbc:postgresql://localhost:5433/db_charge_mcqueen";
            String user = "postgres";
            String password = "postgres";
            conn = DriverManager.getConnection(url, user, password);

            // Vérifier que la connexion a réussi
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie !");

                // Créer une nouvelle instruction SQL
                Statement stmt = conn.createStatement();

                 // Exécuter une requête SQL pour récupérer des données à partir d'une table
                 String sql = "SELECT * FROM Client";
                 ResultSet rs = stmt.executeQuery(sql);

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

}
