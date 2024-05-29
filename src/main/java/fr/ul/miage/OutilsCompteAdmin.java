package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;

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
        if (listeClients.isEmpty()) {
            System.out.println("Aucun client appelé \"" + nom + " " + prenom + "\" n'a été trouvé");
        } else {
            System.out.println("\nVoici les clients trouvés appelés \"" + nom + " " + prenom + "\" :");
            for (int i = 0; i < listeClients.size(); i++) {
                System.out.print("\n" + (i + 1) + ". ");
                System.out.println("Nom : " + listeClients.get(i).getNom());
                System.out.println("Prénom : " + listeClients.get(i).getPrenom());
                System.out.println("Mail : " + listeClients.get(i).getEmail());
            }
        }
    }

    /**
     * Affiche le profil du client sélectionné par l'utilisateur.
     *
     * @param listeClients La liste des clients
     * @param numClient L'index du client sélectionné par l'utilisateur
     */
    private static void afficherProfilClient(List<ClientDto> listeClients, int numClient) {
        System.out.println("""
                _\\|/^
                 (_oo
                  |     
                 /|\\
                  |
                  LL""");
        System.out.println("Voici le profil du client sélectionné :");
        ClientDto client = listeClients.get(numClient);

        System.out.println("Nom : " + client.getNom());
        System.out.println("Prénom : " + client.getPrenom());
        System.out.println("Mail : " + client.getEmail());
        System.out.println("Numéro de téléphone : " + client.getTelephone());
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
        afficherClients(listeClients, nom, prenom);
        numClient = Outils.saisirInt(0, listeClients.size()) - 1;
        afficherProfilClient(listeClients, numClient);
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
        // mock pour simuler le retour de la bdd
        List<ClientDto> clients = new ArrayList<ClientDto>();

        if (BorneMere.getEstAdmin()) {
            // Add some clients to the list
            clients.add(new ClientDto("Doe", "John", "123-456-7890", "john.doe@example.com",
                    "1234-5678-9012-3456"));
            clients.add(new ClientDto("Doe", "John", "987-456-0439", "john.doe2@example.com",
                    "1234-5678-9012-3456"));
            clients.add(new ClientDto("Doe", "John", "567-123-0433", "john.doe3@example.com",
                    "1234-5678-9012-3456"));
            clients.add(new ClientDto("Smith", "Jane", "987-654-3210", "jane.smith@example.com",
                    "5678-9012-3456-7890"));

            return clients;
        }
        return new ArrayList<ClientDto>();
    }
}
