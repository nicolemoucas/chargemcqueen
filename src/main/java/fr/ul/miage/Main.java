package fr.ul.miage;

public class Main {
    public static void main(String[] args) {
        bienvenue();
        afficherMenu();
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