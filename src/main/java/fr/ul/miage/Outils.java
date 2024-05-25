package fr.ul.miage;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
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

    private static final String mdpRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])*(?=.*[@#$!%^&+=])*(?=\\S+$).{8,}$";

    private static final String regexPlaqueImmatriculation = "^[a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2}$";
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

    /**
     * Méthode utilisée pour controler que le nom/prénom du client est valide.
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
     * @param numero le numéro de téléphone à contrôler.
     * @return {boolean} true si le numéro de téléphone est valide, false sinon.
     */
    public static boolean verificationTelephone(String numero) {
        return Pattern.compile(telephoneRegex)
                .matcher(numero)
                .matches();
    }

    /**
     * Méthode utilisée pour controler que le numéro de carte bancaire est valide.
     * @param numeroCarte le numéro de carte bancaire à contrôler.
     * @return {boolean} true si le numéro de carte bancaire est valide, false sinon.
     */
    public static boolean verificationCarteBancaire(String numeroCarte) {
        return Pattern.compile(regexCarteBancaire)
                .matcher(numeroCarte)
                .matches();
    }


    public static boolean verificationMotDePasse(String motDePasse) {
        return Pattern.compile(mdpRegex)
                .matcher(motDePasse)
                .matches();
    }

    public static boolean verificationPlaqueImmatriculation(String immat) {
        return Pattern.compile(regexPlaqueImmatriculation)
                .matcher(immat)
                .matches();
    }


    public static String convertByteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] convertStringToByteArray(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 128;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    public static byte[] generateSalt(int saltLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[saltLength];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(char[] password, byte[] salt) {
        KeySpec keySpec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory secretKeyFactory = null;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
