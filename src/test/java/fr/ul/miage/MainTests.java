package fr.ul.miage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe qui contient les tests pour la classe Main.
 */
@DisplayName("Tests pour la classe Main")
public class MainTests {

    @DisplayName("La saisir de l'utilisateur est un entier")
    @Test
    public void testSaisieEntier() {
        int result = Main.saisirChoixMenu();
        assertInstanceOf(int.class, result);
    }
}
