package fr.ul.miage;

import fr.ul.miage.dtos.MotDePasseDto;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ConnexionCompteClientTest {

    @DisplayName("Retourne true si le mot de passe fourni est le bon.")
    @Test
    public void testisMotDePasseCorrect() throws DecoderException {
        byte[] salt = Outils.generateSalt();
        String inputMdp = "testPassword1234!";
        MotDePasseDto storedMdp = new MotDePasseDto(
        Outils.convertBytesToHex(
                Outils.hashPassword("testPassword1234!", salt)),
                Outils.convertBytesToHex(salt));
        boolean result = new ConnexionCompteClient().isMotDePasseCorrect(inputMdp, storedMdp);
        assertTrue(result);
    }

    @DisplayName("Retourne false si le mot de passe fourni n'est pas le bon.")
    @Test
    public void testisMotDePasseIncorrect() throws DecoderException {
        byte[] salt = Outils.generateSalt();
        String inputMdp = "testPassword1234!";
        MotDePasseDto storedMdp = new MotDePasseDto(
                Outils.convertBytesToHex(
                        Outils.hashPassword("monBeauMotDeP4sse!", salt)),
                Outils.convertBytesToHex(salt));
        boolean result = new ConnexionCompteClient().isMotDePasseCorrect(inputMdp, storedMdp);
        assertFalse(result);
    }
}
