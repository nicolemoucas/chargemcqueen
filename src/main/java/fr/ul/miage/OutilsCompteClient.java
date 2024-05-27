package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;

import java.util.ArrayList;
import java.util.List;


public class OutilsCompteClient {

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
                chercherClient();
            default:
                break;
        }
        return false;
    }

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
    private static void chercherClient() {
        String nom;
        String prenom;
        List<ClientDto> listeClients = new ArrayList<ClientDto>();
        int numClient;

        System.out.println("Chercher un client");
        nom = Outils.saisirString("Nom du client : ");
        prenom = Outils.saisirString("Prénom du client : ");
        listeClients = OutilsCompteClient.getListeClientsBDD(nom, prenom);
        afficherClients(listeClients, nom, prenom);
        numClient = Outils.saisirInt(0, listeClients.size()) - 1;
        afficherProfilClient(listeClients, numClient);
    }

    private static boolean deconnexion() {
        System.out.println("Se déconnecter");
        return true; // stopApp
    }

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
