package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est une boite à outils, et contient des méthodes versatiles pour le Compte administrateur.
 */
public class OutilsCompteAdmin {
    /**
     * Affiche la liste des clients dont le nom et le prénom correspondent aux saisies de l'utilisateur.
     *
     * @param listeClients La liste des clients à afficher
     * @param nom Le nom saisi par l'utilisateur
     * @param prenom Le prénom saisi par l'utilisateur
     */
    protected static void afficherClients(List<ClientDto> listeClients, String nom, String prenom) {
        System.out.println("\nVoici les clients trouvés appelés \"" + prenom + " " + nom + "\" :");
        for (int i = 0; i < listeClients.size(); i++) {
            System.out.print("\n" + (i + 1) + ". ");
            System.out.println("Nom : " + listeClients.get(i).getNom());
            System.out.println("Prénom : " + listeClients.get(i).getPrenom());
            System.out.println("Mail : " + listeClients.get(i).getEmail());
        }
    }

    /**
     * Affiche le profil du client sélectionné par l'utilisateur.
     *
     * @param listeClients La liste des clients
     * @param numClient L'index du client sélectionné par l'utilisateur
     */
    private static void afficherProfilClient(List<ClientDto> listeClients, int numClient) {
        afficherCarlo();
        System.out.println("Voici le profil du client sélectionné :");
        ClientDto client = listeClients.get(numClient);

        System.out.println("Nom : " + client.getNom());
        System.out.println("Prénom : " + client.getPrenom());
        System.out.println("Mail : " + client.getEmail());
        System.out.println("Numéro de téléphone : " + client.getTelephone());
    }

    /**
     * Affiche l'image ASCII art de Carlo.
     */
    private static void afficherCarlo() {
        System.out.println("""
                     .--'''''''''--.
                  .'      .---.      '.
                 /    .-----------.    \\
                /        .-----.        \\
                |       .-.   .-.       |
                |      /   \\ /   \\      |
                 \\    | .-. | .-. |    /
                  '-._| | | | | | |_.-'
                      | '-' | '-' |
                       \\___/ \\___/
                    _.-'  /   \\  `-._
                  .' _.--|     |--._ '.
                  ' _...-|     |-..._ '
                         |     |
                         '.___.'
                           | |
                           | |""");
    }

    /**
     * Affiche les clients à partir d'un nom et prénom saisis par l'utilisateur (qui doit être
     * administrateur).
     */
    protected static void chercherClient() {
        String nom;
        String prenom;
        List<ClientDto> listeClients = new ArrayList<ClientDto>();
        int numClient;

        System.out.println("Chercher un client");
        nom = Outils.saisirString("Nom du client : ");
        prenom = Outils.saisirString("Prénom du client : ");
        listeClients = OutilsCompteAdmin.getListeClientsBDD(nom, prenom);
        if (!listeClients.isEmpty()) {
            afficherClients(listeClients, nom, prenom);
            numClient = Outils.saisirInt(0, listeClients.size()) - 1;
            afficherProfilClient(listeClients, numClient);
        } else {
            System.out.println("Aucun client appelé \"" + prenom + " " + nom + "\" n'a été trouvé.");
        }
    }

    /**
     * Récupère depuis la base de données la liste des clients dont le nom et le prénom correspondent
     * à ceux passés en paramètre.
     *
     * @param nom Le nom du client
     * @param prenom Le prénom du client
     * @return La liste des clients correspondants
     */
    protected static List<ClientDto> getListeClientsBDD(String nom, String prenom) {
        List<ClientDto> clients = new ArrayList<ClientDto>();
        OutilsBaseSQL outilsBaseSQL = new OutilsBaseSQL();
        ResultSet result = null;
        PreparedStatement stmt = null;
        String query = "SELECT nom, prenom, numtelephone, email, numcarte FROM Client WHERE LOWER(nom) like " +
                "LOWER(?) AND LOWER(prenom) LIKE LOWER(?);";
        try {
            stmt = outilsBaseSQL.getConn().prepareStatement(query);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);

            result = stmt.executeQuery();
            while (result.next()) {
                System.out.println(result.getString("nom")+result.getString("email"));
                clients.add(new ClientDto(result.getString("nom"), result.getString("prenom"),
                        result.getString("numtelephone"), result.getString("email"),
                        result.getString("numcarte")));
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
