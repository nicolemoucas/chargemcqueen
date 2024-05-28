package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;
import fr.ul.miage.dtos.CompteClientDto;
import fr.ul.miage.dtos.MotDePasseDto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Scanner;

public class ConnexionCompteClient {

    /**
     * Méthode qui déroule la procédure de connexion d'un client.
     * @return {ClientDto} le profil du client si ses identifiants sont corrects, null sinon.
     */
    public ClientDto procedureConnexionCompte() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("CONNEXION: \n");
        String mail = Outils.recupererMail(scanner);
        if(doesMailExist(mail)) {
            //TODO récupérer le client et son compte en BDD, celui la est un placeholder de test
            ClientDto fetchedClient = new ClientDto(
                    "DUPONT",
                    "Jean",
                    "0612345678",
                    "jean.dupont@gmail.com",
                    "123456789012345");
            CompteClientDto fetchedComptePlaceholder = new CompteClientDto("1", new MotDePasseDto("mdp", "sel"));
            MotDePasseDto motDePasseComptePlaceholder = fetchedComptePlaceholder.getMotDePasse();
            String mdp = recupererMotDePasse(scanner);
            if (isMotDePasseCorrect(mdp, motDePasseComptePlaceholder)) {
                return fetchedClient;
            }
            // TODO : décider si on veut laisser plusieurs essais. 3 ?
            System.out.println("Le mot de passe est incorrect.");
            return null;
        }
        return null;
    }

    /**
     * Méthode utilisée pour s'assurer qu'il existe un client utilisant cet email en base de données.
     * @param mail le mail entré par l'utilisateur.
     * @return true si le mail est utilisé, false sinon.
     */
    private boolean doesMailExist(String mail) {
        return true;
        //TODO : il faut chercher le client dans la base qui a cet email. S'il existe, retourner true, sinon false.
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le mot de passe lors de la connexion.
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} le mot de passe entré par l'utilisateur.
     */
    private String recupererMotDePasse(Scanner scanner) {
        System.out.println("Entrez votre mot de passe: ");
        //ajouter verification, mais jsp quoi
        return scanner.nextLine();
    }

    /**
     * Méthode utilisée pour contrôler le mot de passe entré.
     * @param motDePasseInput le mot de passe entré par l'utilisateur.
     * @param motDePasseBDD le mot de passe stocké en base de données avec le profil du client.
     * @return true si les deux hash sont identiques, false sinon.
     */
    public boolean isMotDePasseCorrect(String motDePasseInput, MotDePasseDto motDePasseBDD) {
        return motDePasseBDD.getMotDePasseChiffre().equals(
                        Outils.convertBytesToHex(Outils.hashPassword(motDePasseInput, Outils.convertHexToBytes(motDePasseBDD.getSel()))));
    }

}
