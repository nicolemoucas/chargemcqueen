package fr.ul.miage;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Cette classe est une boite à outils, et contient des méthodes versatiles.
 */
public class Outils {

    /**
     * Constante qui contient le pattern d'adresse mail acceptée par l'application.
     */
    private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9]+([_.-][A-Za-z0-9]+)*@[A-Za-z0-9]+([-.][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";

    /**
     * Constante qui contient le pattern de noms acceptés par l'application.
     */
    private static final String nomsRegex = "^[A-Za-zÀ-ÿ](?=.{1,29}$)[A-Za-zÀ-ÿ]*((?:\\h+|-)[A-ZÀ-ÿ][A-Za-zÀ-ÿ]*)*$";

    /**
     * Constante qui contient le pattern de numéros de téléphone acceptés par l'application.
     */
    private static final String telephoneRegex = "^(\\+33[1-9][0-9]{8})|(0[1-9][0-9]{8})$";

    /**
     * Constante qui contient le pattern de numéros de carte bancaire acceptés par l'application.
     */
    private static final String regexCarteBancaire = "^[0-9]{15,16}$";

    /**
     * Méthode utilisée pour controler que l'adresse mail est valide.
     *
     * @param mail l'adresse mail à contrôler.
     * @return {boolean} true si l'adresse est valide, false sinon.
     */
    public static boolean verificationMails(String mail) {
        return Pattern.compile(emailRegex)
                .matcher(mail)
                .matches();
    }

    /**
     * Méthode utilisée pour controler que le nom/prénom du client est valide.
     *
     * @param nom le nom ou prénom à contrôler.
     * @return {boolean} true si le nom/prénom est valide, false sinon.
     */
    public static boolean verificationNoms(String nom) {
        return Pattern.compile(nomsRegex)
                .matcher(nom)
                .matches();
    }

    /**
     * Méthode utilisée pour controler que le numéro de téléphone est valide.
     *
     * @param numero le numéro de téléphone à contrôler.
     * @return {boolean} true si le numéro de téléphone est valide, false sinon.
     */
    public static boolean verificationTelephone(String numero) {
        return Pattern.compile(telephoneRegex)
                .matcher(numero)
                .matches();
    }

    /**
     * Méthode utilisée pour contrôler que le numéro de carte bancaire est valide.
     *
     * @param numeroCarte le numéro de carte bancaire à contrôler.
     * @return {boolean} true si le numéro de carte bancaire est valide, false sinon.
     */
    public static boolean verificationCarteBancaire(String numeroCarte) {
        return Pattern.compile(regexCarteBancaire)
                .matcher(numeroCarte)
                .matches();
    }

    /**
     * Méthode qui permet la saisie d'un string jusqu'à ce qu'elle soit correcte.
     *
     * @param message Message pour inviter l'utilisateur à saisir un message
     * @return Chaîne de caractères saisie
     */
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
