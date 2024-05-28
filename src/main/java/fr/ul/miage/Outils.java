package fr.ul.miage;

import fr.ul.miage.dtos.MotDePasseDto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Cette classe est une boite à outils, et contient des méthodes versatiles.
 */
public class Outils {

    /**
     * Constante qui contient le pattern d'adresse mail acceptées par l'application.
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
    private static final String regexCarteBancaire = "^[0-9]{14,16}$";

    /**
     * Constante qui contient le pattern de mots de passe acceptés par l'application.
     */
    private static final String mdpRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])*(?=.*[@#$!%^&+=])*(?=\\S+$).{8,}$";

    /**
     * Constante qui contient le pattern de numéros de plaque d'immatriculation acceptés par l'application.
     */
    private static final String regexPlaqueImmatriculation = "^[a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2}$";

    /**
     * Constante qui définit la longueur du sel utilisé pour chiffrer les mots de passe..
     */
    private static final int SALT_LENGTH = 16;

    /**
     * Constante qui définit le nombre d'itérations de la méthode qui chiffre le mot de passe.
     */
    private static final int HASH_ITERATIONS = 10000;

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
     * Méthode utilisée pour contrôler que le nom/prénom du client est valide.
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
     * Méthode utilisée pour contrôler que le numéro de téléphone est valide.
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
     * Méthode utilisée pour contrôler que le mot de passe est valide.
     *
     * @param motDePasse le mot de passe à contrôler.
     * @return {boolean} true si le mot de passe est valide, false sinon.
     */
    public static boolean verificationMotDePasse(String motDePasse) {
        return Pattern.compile(mdpRegex)
                .matcher(motDePasse)
                .matches();
    }

    /**
     * Méthode utilisée pour contrôler que le numéro de la plaque d'immatriculation est valide.
     *
     * @param immat le numéro de la plaque d'immatriculation à contrôler.
     * @return {boolean} true si le numéro de la plaque d'immatriculation est valide, false sinon.
     */
    public static boolean verificationPlaqueImmatriculation(String immat) {
        return Pattern.compile(regexPlaqueImmatriculation)
                .matcher(immat)
                .matches();
    }

    /**
     * Méthode utilisée pour générer aléatoirement du sel pour chiffrer les mots de passe.
     * @return {byte[]} le sel généré.
     */
    protected static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Méthode appelée par hashPassword(String) pour chiffrer le mot de passe.
     * @param password le mot de passe qu'on veut chiffrer.
     * @param salt le sel qui sert à chiffrer le mot de passe.
     * @return le tableau de byte qui correspond au mot de passe "password" chiffré.
     */
    static byte[] hashPassword(String password, byte[] salt) {
        MessageDigest messageDigest = null;
        byte[] hash = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.reset();
            messageDigest.update(salt);
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < HASH_ITERATIONS; i++) {
                hash = messageDigest.digest(hash);
            }
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception here
            e.printStackTrace();
        }

        return hash;
    }

    /**
     * Méthode utilisée pour hasher le mot de passe donné par l'utilisateur.
     * @param password le mot de passe de l'utilisateur.
     * @return {MotDePasseDto} le mot de passe construit à partir du sel et du mot de passe chiffré.
     */
    public static MotDePasseDto hashPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = hashPassword(password, salt);
        return new MotDePasseDto(convertBytesToHex(hash), convertBytesToHex(salt));
    }

    /**
     * Méthode utilisée pour convertir un tableau de bytes en chaine hexadecimale.
     * @param bytes le tableau de bytes a convertir.
     * @return la chaine hexadécimale convertie.
     */
    protected static String convertBytesToHex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    /**
     * Méthode utilisée pour convertir un Hexadécimal en tableau de bytes.
     * @param hex la chaine hexadécimale a convertir.
     * @return le tableau de bytes converti.
     */
    protected static byte[] convertHexToBytes(String hex) {
        try {
            return Hex.decodeHex(hex);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
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
     * Méthode utilisée pour récupérer les inputs utilisateurs pour l'adresse mail.
     * On continue de lui demander de rentrer une adresse mail tant que son adresse est invalide.
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} l'adresse mail valide de l'utilisateur.
     */

    protected static String recupererMail(Scanner scanner) {
        System.out.println("Entrez votre adresse mail :");
        String mail = scanner.nextLine();
        while(!Outils.verificationMails(mail)){
            System.out.println("Votre adresse mail doit contenir un @, et être de la forme suivante : \"adresse@domain.ext\". \n" +
                    "Veuillez entrer une adresse mail valide :");
            mail = scanner.nextLine();
        }
        return mail;
    }

}
