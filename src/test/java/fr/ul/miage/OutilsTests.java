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

}
