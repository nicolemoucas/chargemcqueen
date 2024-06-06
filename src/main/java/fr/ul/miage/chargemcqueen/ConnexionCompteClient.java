package fr.ul.miage.chargemcqueen;

import fr.ul.miage.chargemcqueen.dtos.ClientDto;
import fr.ul.miage.chargemcqueen.dtos.CompteClientDto;
import fr.ul.miage.chargemcqueen.dtos.MotDePasseDto;

import java.util.List;
import java.util.Scanner;

public class ConnexionCompteClient {

    /**
     * Méthode qui déroule la procédure de connexion d'un client.
     *
     * @return {ClientDto} le profil du client si ses identifiants sont corrects, null sinon.
     */
    public ClientDto procedureConnexionCompte() {
        Scanner scanner = new Scanner(System.in);
        String mail = Outils.recupererMail(scanner);

        // Récupérer le client et son compte (respectivement) depuis la base
        List<Object> client = OutilsCompte.getClientBDD(mail);
        if (client.getFirst() == null || client.getLast() == null) {
            System.out.println("Aucun client n'existe avec le mail '" + mail + "', veuillez vous inscrire.");
        } else {
            ClientDto clientDto = (ClientDto) client.getFirst();
            CompteClientDto compteClientDto = (CompteClientDto) client.getLast();
            if (verifierMdpConnexion(scanner, compteClientDto)) {
                return clientDto;
            }
        }
        return null;
    }

    private boolean verifierMdpConnexion(Scanner scanner, CompteClientDto compteclient) {
        int nbEssaisLeft = 3;
        
        while (nbEssaisLeft > 0) {
            MotDePasseDto mdpCompte = compteclient.getMotDePasse();
            String mdpSaisi = recupererMotDePasse(scanner);
            if (isMotDePasseCorrect(mdpSaisi, mdpCompte)) {
                return true;
            }
            nbEssaisLeft--;
            System.out.println("Le mot de passe est incorrect.\nVous avez encore " + nbEssaisLeft + " essai(s).");
        }
        System.out.println("L'accès à votre compte a été bloqué. Veuillez contacter un administrateur.");
        return false;
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le mot de passe lors de la connexion.
     *
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
     *
     * @param motDePasseInput le mot de passe entré par l'utilisateur.
     * @param motDePasseBDD le mot de passe stocké en base de données avec le profil du client.
     * @return true si les deux hash sont identiques, false sinon.
     */
    public boolean isMotDePasseCorrect(String motDePasseInput, MotDePasseDto motDePasseBDD) {
        return motDePasseBDD.getMotDePasseChiffre().equals(
                        Outils.convertBytesToHex(Outils.hashPassword(motDePasseInput, Outils.convertHexToBytes(motDePasseBDD.getSel()))));
    }

}
