package fr.ul.miage.chargemcqueen;

import fr.ul.miage.chargemcqueen.dtos.ClientDto;
import fr.ul.miage.chargemcqueen.dtos.CompteClientDto;
import fr.ul.miage.chargemcqueen.dtos.MotDePasseDto;

import java.util.Scanner;

import static fr.ul.miage.chargemcqueen.Outils.checkYesOrNoAnswer;
import static fr.ul.miage.chargemcqueen.Outils.recupererPlaqueImmat;

/**
 * Classe qui gère le formulaire d'inscription d'un nouveau client.
 */
public class FormulaireInscriptionClient {

    /**
     * Méthode qui déroule la procédure d'inscription de l'utilisateur, et qui construit l'objet Client
     * une fois toutes les informations récupérées.
     *
     * @return {ClientDto} le client ainsi inscrit pour qu'il soit rajouté en base de données.
     */
    public ClientDto procedureInscription() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("FORMULAIRE D'INSCRIPTION: \n");
        String nomDeFamille = recupererNomDeFamille(scanner);
        String prenom = recupererPrenom(scanner);
        String telephone = recupererTelephone(scanner);
        String mail = Outils.recupererMail(scanner);
        String carteBancaire = recupererCarteBancaire(scanner);
        // Le compte créé pour l'insérer en BDD
        CompteClientDto compteClient = creerCompteClient(scanner);
        plaqueDImmatriculation(scanner);
        return new ClientDto(nomDeFamille, prenom, telephone, mail, carteBancaire);
    }

    /**
     * Méthode utilisée pour créer le compte de l'utilisateur.
     *
     * @param scanner le scanner qui écoute les inputs utilisateurs.
     * @return le compte créé
     */
    private CompteClientDto creerCompteClient(Scanner scanner){
        String motDePasse = recupererMotDePasse(scanner);
        MotDePasseDto motDePasseChiffre = Outils.hashPassword(motDePasse);
        return new CompteClientDto(1, motDePasseChiffre);
    }

    /**
     * Méthode utilisée pour écouter les inputs utilisateurs dans le cadre de l'ajout d'une plaqwue d'immatriculation lors de l'inscription .
     * L'utilisateur n'est pas obligé de le faire, donc on lui laisse le choix.
     *
     * @param scanner le scanner qui écoute les inputs utilisateurs.
     */
    private void plaqueDImmatriculation(Scanner scanner) {
        System.out.println("Souhaitez-vous entrer la plaque d'immatriculation de votre véhicule personnel ? \n" +
                "⚠ Ne choisissez jamais cette option pour un véhicule de location.\n" +
                "O - Oui / N - Non");
        String choix = scanner.nextLine();
        int timeout = 0;
        while(!checkYesOrNoAnswer(choix)|| timeout >= 3) {
            timeout++;
            System.out.println("Je n'ai pas compris votre choix. \n" +
                    "Souhaitez-vous entrer la plaque d'immatriculation de votre véhicule personnel ? \n" +
                    "⚠ Ne choisissez jamais cette option pour un véhicule de location.\n" +
                    "O - Oui / N - Non");
            choix = scanner.nextLine();
        }
        if(choix.equalsIgnoreCase("o")) {
            String immatriculation = recupererPlaqueImmat(scanner);
            System.out.println("Votre plaque d'immatriculation : " + immatriculation);
        }
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour la carte bancaire.
     * On continue de lui demander de rentrer un numéro tant que son numéro est invalide.
     *
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} le numéro de carte bancaire valide de l'utilisateur.
     */
    private String recupererCarteBancaire(Scanner scanner) {
        System.out.println("Entrez votre numéro de carte bancaire sans espaces ou séparateurs :");
        String carteBancaire = scanner.nextLine();
        while(!Outils.verificationCarteBancaire(carteBancaire)) {
            System.out.println("Votre numéro de carte ne peut pas contenir de lettres ou caractères spéciaux et doit contenir entre 14 et 16 caractères. \n" +
                    "Veuillez entrer un numéro de carte bancaire valide : " );
            carteBancaire = scanner.nextLine();
        }
        return carteBancaire;
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le numéro de téléphone.
     * On continue de lui demander de rentrer un numéro tant que son numéro est invalide.
     *
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} le numéro de téléphone valide de l'utilisateur.
     */
    private String recupererTelephone(Scanner scanner) {
        System.out.println("Entrez votre numéro de téléphone sans espaces ou séparateurs :");
        String telephone = scanner.nextLine();
        while(!Outils.verificationTelephone(telephone)) {
            System.out.println("Votre numéro de téléphone doit respecter ce format : 0XXXXXXXXX ou +33XXXXXXXXX \n" +
                    "Veuillez entrer un numéro de téléphone valide : " );
            telephone = scanner.nextLine();
        }
        return telephone;
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le prénom de l'utilisateur.
     * On continue de lui demander de rentrer un prénom tant que son prénom est invalide.
     *
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} le prénom valide de l'utilisateur.
     */
    private String recupererPrenom(Scanner scanner) {
        System.out.println("Entrez votre prénom :");
        String prenom = scanner.nextLine();
        while(!Outils.verificationNoms(prenom)) {
            System.out.println("Votre prénom ne peut pas contenir de chiffres ou caractères spéciaux.\n" +
                    "Veuillez entrer un prénom valide :");
            prenom = scanner.nextLine();
        }
        return prenom;
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le nom de famille de l'utilisateur.
     * On continue de lui demander de rentrer un nom tant que son nom est invalide.
     *
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} le nom de famille valide de l'utilisateur.
     */
    private String recupererNomDeFamille(Scanner scanner) {
        System.out.println("Entrez votre nom de famille : ");

        String nomDeFamille = scanner.nextLine();

        while(!Outils.verificationNoms(nomDeFamille)) {
            System.out.println("Votre nom de famille ne peut pas contenir de chiffres ou caractères spéciaux.\n" +
                    "Veuillez entrer un nom de famille valide :");
            nomDeFamille = scanner.nextLine();
        }
        return nomDeFamille;
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le mot de passe de l'utilisateur.
     * On continue de lui demander de rentrer un mot de passe tant que celui choisi ne respecte pas les contraintes.
     *
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} le mot de passe valide de l'utilisateur.
     */
    protected static String recupererMotDePasse(Scanner scanner) {
        System.out.println("Entrez votre mot de passe : ");

        String motDePasse = scanner.nextLine();

        while(!Outils.verificationMotDePasse(motDePasse)) {
            System.out.println("Votre mot de passe doit faire au moins 8 caractères de long, et contenir des chiffres et des lettres.\n" +
                    "Veuillez choisir un autre mot de passe : ");
            motDePasse = scanner.nextLine();
        }
        return motDePasse;
    }
}
