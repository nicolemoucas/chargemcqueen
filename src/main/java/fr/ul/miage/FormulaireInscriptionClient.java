package fr.ul.miage;

import fr.ul.miage.dtos.ClientDto;

import java.util.Scanner;

public class FormulaireInscriptionClient {

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

    private static void plaqueDImmatriculation(Scanner scanner) {
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
            //enregistrer imat
        }
    }

    private static String recupererCarteBancaire(Scanner scanner) {
        System.out.println("Entrez votre numéro de carte bancaire sans espaces ou séparateurs :");
        String carteBancaire = scanner.nextLine();
        while(!Outils.verificationCarteBancaire(carteBancaire)) {
            System.out.println("Votre numéro de carte ne peut pas contenir de lettres ou caractères spéciaux et doit contenir entre 14 et 16 caractères. \n" +
                    "Veuillez entrer un numéro de carte bancaire valide : " );
            carteBancaire = scanner.nextLine();
        }
        return carteBancaire;
    }

    private static String recupererMail(Scanner scanner) {
        System.out.println("Entrez votre adresse mail :");
        String mail = scanner.nextLine();
        while(!Outils.verificationMails(mail)){
            System.out.println("Votre adresse mail doit contenir un @, et être de la forme suivante : \"adresse@domain.ext\". \n" +
                    "Veuillez entrer une adresse mail valide :");
            mail = scanner.nextLine();
        }
        return mail;
    }

    private static String recupererTelephone(Scanner scanner) {
        System.out.println("Entrez votre numéro de téléphone sans espaces ou séparateurs :");
        String telephone = scanner.nextLine();
        while(!Outils.verificationTelephone(telephone)) {
            System.out.println("Votre numéro de téléphone doit respecter ce format : 0XXXXXXXXX ou +33XXXXXXXXX \n" +
                    "Veuillez entrer un numéro de téléphone valide : " );
            telephone = scanner.nextLine();
        }
        return telephone;
    }

    private static String recupererPrenom(Scanner scanner) {
        System.out.println("Entrez votre prénom :");
        String prenom = scanner.nextLine();
        while(!Outils.verificationNoms(prenom)) {
            System.out.println("Votre prénom ne peut pas contenir de chiffres ou caractères spéciaux.\n" +
                    "Veuillez entrer un prénom valide :");
            prenom = scanner.nextLine();
        }
        return prenom;
    }

    private static String recupererNomDeFamille(Scanner scanner) {
        System.out.println("Entrez votre nom de famille : \n");

        String nomDeFamille = scanner.nextLine();

        while(!Outils.verificationNoms(nomDeFamille)) {
            System.out.println("Votre nom de famille ne peut pas contenir de chiffres ou caractères spéciaux.\n" +
                    "Veuillez entrer un nom de famille valide :");
            nomDeFamille = scanner.nextLine();
        }
        return nomDeFamille;
    }
}
