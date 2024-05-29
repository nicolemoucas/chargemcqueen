package fr.ul.miage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe est une boite à outils, et contient des méthodes versatiles liées à la base de données
 * qui utilisent JDBC.
 */
public class OutilsBaseSQL {

    /**
     * L'instance unique de la classe OutilsBaseSQL.
     */
    private static OutilsBaseSQL instance;
    /**
     * L'objet Connection qui représente la connexion à la base de données.
     */
    private static Connection conn = null;

    /**
     * Constructeur privé pour la classe OutilsBaseSQL
     */
    private OutilsBaseSQL(){
        makeConnexion();
    }

    /**
     * Méthode pour établir une connexion à la base de données si elle n'existe pas.
     */
    public void makeConnexion(){
        if (conn == null){
            try {
                // Charger le pilote JDBC PostgreSQL
                Class.forName("org.postgresql.Driver");

                // Créer une nouvelle connexion à la base de données PostgreSQL
                // Penser à exécuter le script de création de BDD avant
                String url = "jdbc:postgresql://localhost:5433/db_charge_mcqueen";
                String user = "postgres";
                String password = "postgres";
                conn = DriverManager.getConnection(url, user, password);

                // Vérifier que la connexion a réussi
                if (conn != null) {
                    System.out.println("Connexion à la base de données réussie !");
                }
            } catch (ClassNotFoundException e) {
                // Le pilote JDBC n'a pas été trouvé dans le chemin de classe
                System.err.println(e.getMessage());
            } catch (SQLException e) {
                // Une erreur s'est produite lors de la connexion à la base de données
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Méthode pour obtenir l'instance unique de la classe OutilsBaseSQL si elle existe, sinon elle
     * est créée.
     *
     * @return L'instance unique de la classe OutilsBaseSQL
     */
    public static OutilsBaseSQL getInstance() {
        if (instance == null){
            instance = new OutilsBaseSQL();
        }
        return instance;
    }

    /**
     * Méthode qui ferme la connexion à la base de données si elle existe.
     */
    public static void fermerConnexion() {
        try {
            if (conn != null) {
                // Fermer la connexion à la base de données
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Méthode pour obtenir l'objet Connection qui représente la connexion à la base de données.
     *
     * @return L'object Connection
     */
    public static Connection getConn() {
        return conn;
    }
}
