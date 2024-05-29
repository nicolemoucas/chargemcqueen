package fr.ul.miage;

import fr.ul.miage.dtos.MotDePasseDto;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class InscriptionCompteClientTest {

    @DisplayName("Retourne true si le hash du mot de passe est le bon.")
    @Test
    public void testIsHashCorrect()  {
        String storedHash = "3290af1edf6bb787a44a40fae4837a13b6151b4beb5e2c66d88ccf513cf279026937c8bf4755a58a9d7291cb8262a984ac6559a7562569f5dfd8119d3a0c2a29";
        byte[] salt = Outils.convertHexToBytes("681074c9b1203ab4089c6454868d7c99");
        String mdp = "1234qwer";
        String testHash = Outils.convertBytesToHex(Outils.hashPassword(mdp, salt));
        assertEquals(testHash, storedHash);
    }

    @DisplayName("Retourne false si le hash du mot de passe est le bon.")
    @Test
    public void testIsHashCorrect2()  {
        String storedHash = "3290af1edf6bb787a44a40fae4837a13b6151c4beb5e2c66d88ccf513cf279026937c8bf4755a58a9d7291cb8262a984ac6559a7562569f5dfd8119d3a0c2a29";
        byte[] salt = Outils.convertHexToBytes("681074c9b1203ab4089c6454868d7c99");
        String mdp = "1234qwer";
        String testHash = Outils.convertBytesToHex(Outils.hashPassword(mdp, salt));
        assertNotEquals(testHash, storedHash);
    }

    @DisplayName("Retourne false si le hash du mot de passe est le bon.")
    @Test
    public void testIsHashCorrect3()  {
        String storedHash = "fa73";
        byte[] salt = Outils.generateSalt();
        String mdp = "Ch1ck3nNug";
        String testHash = Outils.convertBytesToHex(Outils.hashPassword(mdp, salt));
        System.out.println("salt: " + Outils.convertBytesToHex(salt));
        assertEquals(storedHash, testHash);
    }
}
