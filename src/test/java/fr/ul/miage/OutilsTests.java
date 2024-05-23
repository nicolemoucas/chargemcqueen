package fr.ul.miage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe qui contient les tests pour la classe Outils.
 */
@DisplayName("Tests sur la classe Outils")
public class OutilsTests {

    @DisplayName("Les adresses correctes passent.")
    @Test
    public void testAdressesCorrectes() {
        String mail = "adresse@gmail.com";
        boolean result = Outils.verificationMails(mail);
        assertTrue(result);
    }

    @DisplayName("Les adresses vides ne passent pas.")
    @Test
    public void testAdressesVides() {
        String mail = "";
        boolean result = Outils.verificationMails(mail);
        assertFalse(result);
    }

    @DisplayName("Les adresses null génèrent une exception.")
    @Test
    public void testAdressesNull() {
        String mail = null;
        assertThatThrownBy(() -> Outils.verificationMails(mail))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest(name= "Les adresses incorrectes ne passent pas.")
    @CsvSource({
            "adresse.@gmail.com",
            ".adresse@gmail.com",
            "adresse@gmail.com.",
            "adresse.@gmail.com",
            "adresse-@gmail.com",
            "-adresse@gmail.com",
            "adresse@gmail.com-",
            "adresse-@gmail.com",
            "@adresse@gmail.com",
            "@gmail.com",
            "adresse@.com",
            "adresse@gmail",
            "adresse.gmail.com"

    })
    public void testAdressesIncorrectes(String mail) {
        boolean result = Outils.verificationMails(mail);
        System.out.println(mail);
        assertFalse(result);
    }

    @ParameterizedTest(name= "Les noms corrects passent.")
    @CsvSource({
            "Nadège",
            "Nicole",
            "Marie-Clémentine",
            "Marie Clémentine",
            "Aña"

    })
    public void prenomsCorrects(String nom) {
        boolean result = Outils.verificationNoms(nom);
        System.out.println(nom);
        assertTrue(result);
    }

    @ParameterizedTest(name= "Les noms incorrects ne passent.")
    @CsvSource({
            "1234",
            "N!cole",
            "@Clémentine",
            "Marie@Clémentine",
            "Aña{}"

    })
    public void prenomsIncorrects(String nom) {
        boolean result = Outils.verificationNoms(nom);
        System.out.println(nom);
        assertFalse(result);
    }

    @ParameterizedTest(name= "Les numéros de téléphones qui ont le bon format sont acceptés.")
    @CsvSource({
            "0643825729",
            "+33643825729"
    })
    public void numeroTelephoneCorrects(String numero) {
        boolean result = Outils.verificationTelephone(numero);
        System.out.println(numero);
        assertTrue(result);
    }

    @ParameterizedTest(name= "Les numéros de téléphones qui ont le mauvais format ne sont pas acceptés.")
    @CsvSource({
            "990643825729",
            "+336RR124567",
            "++33387762641"
    })
    public void numeroTelephoneIncorrects(String numero) {
        boolean result = Outils.verificationTelephone(numero);
        System.out.println(numero);
        assertFalse(result);
    }

    @ParameterizedTest(name= "Les numéros de carte qui ont le bon format sont acceptés.")
    @CsvSource({

            "4970119716321974",
            "5200828282828210",
            "371449635398431",
            "6011014433546101"

})
    public void numeroCarteBancaireCorrect(String numeroCarte) {
        boolean result = Outils.verificationCarteBancaire(numeroCarte);
        System.out.println(numeroCarte);
        assertTrue(result);
    }

    @ParameterizedTest(name= "Les numéros de carte qui ont le bon format sont acceptés.")
    @CsvSource({

            "1974",
            "vz200zerf2828282",
            "371449@35-98431",
            "DROP DATABASE NAME;"

    })
    public void numeroCarteBancaireIncorrects(String numeroCarte) {
        boolean result = Outils.verificationCarteBancaire(numeroCarte);
        System.out.println(numeroCarte);
        assertFalse(result);
    }

}
