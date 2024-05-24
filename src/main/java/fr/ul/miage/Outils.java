package fr.ul.miage;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Outils {

    /**
     * Constante qui contient le pattern d'adresse mail acceptée par l'application.
     */
    private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9]+([_.-][A-Za-z0-9]+)*@[A-Za-z0-9]+([-.][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";

    /**
     * Méthode utilisée pour controler que l'adresse mail est valide.
     * @param mail l'adresse mail à contrôler.
     * @return {boolean} true si l'adresse est valide, false sinon.
     */
    public static boolean verificationMails(String mail) {
        return Pattern.compile(emailRegex)
                .matcher(mail)
                .matches();
    }

    protected static String saisirString(String message) {
        Scanner scanner = new Scanner(System.in);
        String saisie = "";
        while (saisie.isEmpty()) {
            System.out.print(message);
            saisie = scanner.nextLine();
        }
        return saisie;
    }
}
