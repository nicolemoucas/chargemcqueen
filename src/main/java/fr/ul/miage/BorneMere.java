package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;

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
                    stopApp = executerOrdreMenuCompte(ordreUtilisateur);
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
        int choixSaisi = saisirInt(0, optionsMenu.size());
        return choixSaisi;
    }

    protected static int saisirInt(int min, int max) {
        int choixSaisi = -1;
        Scanner scanner = new Scanner(System.in);
        String messageErreur = "/!\\ Erreur : veuillez saisir un nombre entre 1 et " + max;

        while (choixSaisi <= min || choixSaisi > max) {
            try {
                System.out.print("\nQuel est votre choix ? : ");
                choixSaisi = scanner.nextInt();
                scanner.nextLine();
                if (choixSaisi <= min || choixSaisi > max) {
                    System.out.println(messageErreur);
                }
            } catch (InputMismatchException e) {
                System.out.println(messageErreur);
                scanner.nextLine();
            }
        }
        System.out.println();
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

    /**
     * Redirige vers l'action choisie par l'utilisateur en fonction du numéro d'ordre passé en paramètre
     * pour le menu quand le client est connecté à son compte.
     *
     * @param ordreUtilisateur Instruction (int) choisie par l'utilisateur
     */
    private static boolean executerOrdreMenuCompte(int ordreUtilisateur) {
        switch (ordreUtilisateur) {
            case 1:
                chercherReservation();
                break;
            case 2:
                faireReservation();
                break;
            case 3:
                ajouterPlaque();
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

    private static void ajouterPlaque() {
        System.out.println("Ajouter un véhicule");
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
        listeClients = getListeClientsBDD(nom, prenom);
        afficherClients(listeClients, nom, prenom);
        numClient = saisirInt(0, listeClients.size()) - 1;
        afficherProfilClient(listeClients, numClient);
    }

    private static List<ClientDto> getListeClientsBDD(String nom, String prenom) {
        // mock pour simuler le retour de la bdd
        List<ClientDto> clients = new ArrayList<ClientDto>();

        if (estAdmin) {
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

    private static void afficherClients(List<ClientDto> listeClients, String nom, String prenom) {
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

    private static boolean deconnexion() {
        System.out.println("Se déconnecter");
        return true; // stopApp
    }

    private static void faireReservation() {
        System.out.println("Faire une réservation");
    }

    private static void chercherReservation() {
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