package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;

import java.util.Scanner;


public class FormulaireInscriptionClient {

    /**
     * Méthode qui déroule la procédure d'inscription de l'utilisateur, et qui construit l'objet Client une fois toutes les informations récupérées.
     * @return {ClientDto} le client ainsi inscrit pour qu'il soit rajouté en base de données.
     */
    public ClientDto procedureInscription() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("FORMULAIRE D'INSCRIPTION: \n");
        String nomDeFamille = recupererNomDeFamille(scanner);
        String prenom = recupererPrenom(scanner);
        String telephone = recupererTelephone(scanner);
        String mail = recupererMail(scanner);
        String carteBancaire = recupererCarteBancaire(scanner);
        plaqueDImmatriculation(scanner);
        System.out.println("Client enregistré avec succès ! Bienvenue à vous, " + prenom + ".");
        return new ClientDto(nomDeFamille, prenom, telephone, mail, carteBancaire);
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
        while((!choix.equalsIgnoreCase("o") && !choix.equalsIgnoreCase("n"))|| timeout >= 3) {
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
     * Méthode utilisée pour récupérer les inputs utilisateurs pour le numéro de plaque d'immatriculation.
     * On continue de lui demander de rentrer un numéro tant que sa plaque est invalide.
     *
     * @param scanner le scanner qui va écouter les réponses.
     * @return {String} la plaque d'immatriculation valide de l'utilisateur.
     */
    private String recupererPlaqueImmat(Scanner scanner) {
        System.out.println("Entrez votre numéro de plaque d'immatriculation sans séparateurs ni espaces :");
        String immat = scanner.nextLine().toUpperCase();
        while(!Outils.verificationPlaqueImmatriculation(immat)){
            System.out.println("Votre numéro de plaque d'immatriculation doit respecter la forme suivante : AA123BB\n" +
                    "Veuillez entrer un numéro de plaque d'immatriculation valide.");
            immat = scanner.nextLine().toUpperCase();
        }
        return immat;
    }

    /**
     * Méthode utilisée pour récupérer les inputs utilisateurs pour l'adresse mail.
     * On continue de lui demander de rentrer une adresse mail tant que son adresse est invalide.
     *
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
