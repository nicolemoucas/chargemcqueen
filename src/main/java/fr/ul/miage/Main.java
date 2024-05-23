package fr.ul.miage;

import java.util.*;

public class Main {
    private static List<String> optionsMenu = new ArrayList<>();
    public static void main(String[] args) {
        int choixMenu;

        optionsMenu = chargerOptionsMenu();
        bienvenue();
        choixMenu = saisirChoixMenu();
        aurevoir();
    }

    private static List<String> chargerOptionsMenu() {
        return new ArrayList<String>(Arrays.asList("Trouver ma réservation",
                "Saisir ma plaque d'immatriculation", "S'inscrire", "Se connecter"));
    }

    public static void bienvenue() {
        System.out.println("Bienvenue chez Charge McQueen !");
        System.out.println(
                "           .--------.\n" +
                        "      ____/_____|___ \\___\n" +
                        "     O    _   - |   _   ,*\n" +
                        "      '--(_)-------(_)--'  \n\n");
    }

    public static void aurevoir() {
        System.out.println("\nÀ bientôt chez Charge McQueen⚡️ !");
        System.out.println(
                "          ______\n" +
                "  *-- *- /|_||_\\`.__\n" +
                "*-- *-  (   _    _ _\\\n" +
                "  *- *--`-(_)--(_)-'");
    }

    public static void afficherMenu() {
        for (int i = 0; i < optionsMenu.size(); i++) {
            System.out.println((i+1) + ". " + optionsMenu.get(i));
        }
    }

    public static int saisirChoixMenu() {
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
}