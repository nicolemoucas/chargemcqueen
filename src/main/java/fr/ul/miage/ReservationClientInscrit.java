package fr.ul.miage;

import fr.ul.miage.Enums.ETypeImmat;
import fr.ul.miage.dtos.ImmatriculationDto;
import fr.ul.miage.dtos.ReservationDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static fr.ul.miage.Outils.checkYesOrNoAnswer;
import static fr.ul.miage.Outils.miseEnFormeImmat;

/**
 * Classe qui permet de gérer la création de réservations pour les clients inscrits.
 */
public class ReservationClientInscrit {

    /**
     * La procédure de réservation.
     * @return l'objet réservation créé à l'issue de la procédure. Null si aucune borne n'est disponible ou si la procédure a été abanadonnée.
     */
    public ReservationDto procedureReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("RESERVER : ");
        //TODO : remplacer ça par les vraies bornes
        List<Integer> bornesDispo = mockListeBornesDispo();
        if (bornesDispo.isEmpty()) {
            System.out.println("Il n'y a pas de bornes disponibles actuellement, veuillez réessayer plus tard.");
            return null;
        }

        int selectedBorneId = selectBorne(bornesDispo);
        ImmatriculationDto plaqueChoisie = choisirPlaque(scanner);

        // si on n'a pas choisi de plaque valable, on va abandonner la réservation.
        if (plaqueChoisie == null) {
            System.out.println("Reservation abandonnée.");
            return null;
        }

        ReservationDto reservationDto = creerReservation(plaqueChoisie, selectedBorneId);
        afficherRecapReservation(plaqueChoisie, reservationDto);
        return reservationDto;
    }

    /**
     * Méthode utilisée pour choisir si on veut utiliser un véhicule pré-enregistré ou un véhicule de location.
     *
     * @param scanner le scanner utilisé pour écouter les inputs utilisateur.
     * @return {ImmatriculationDto} la plaque d'immatriculation sélectionnée.
     */
    private ImmatriculationDto choisirPlaque(Scanner scanner) {
        System.out.println("Souhaitez-vous utiliser un de vos véhicules enregistrés ?\n" +
                "O - Oui / N - Non");
        String choix = scanner.nextLine();

        while (!checkYesOrNoAnswer(choix)) {
            System.out.println("""
                    Je n'ai pas compris votre choix.\s
                    Souhaitez-vous utiliser un de vos véhicules enregistrés ?\s
                    O - Oui / N - Non
                    """);
            choix = scanner.nextLine();
        }

        if (choix.equalsIgnoreCase("o") || choix.equalsIgnoreCase("oui")) {
            return choisirPlaqueEnregistree(scanner);
        } else if (choix.equalsIgnoreCase("n") || choix.equalsIgnoreCase("non")) {
            return ajouterPlaqueTemporaire(scanner);
        }

        return null;
    }

    private ImmatriculationDto choisirPlaqueEnregistree(Scanner scanner) {
        List<ImmatriculationDto> immatriculationDtos = mockListeImmatsClient();

        if (!immatriculationDtos.isEmpty()) {
            System.out.println(afficherPlaquesImmatriculation(immatriculationDtos));
            int indexPlaque = Outils.saisirInt(0, immatriculationDtos.size());
            return immatriculationDtos.get(indexPlaque - 1);
        } else {
            System.out.println("Vous n'avez aucun véhicule enregistré dans votre compte. \n" +
                    "Souhaitez-vous utiliser une plaque temporaire ou abandonner la réservation ? \n" +
                    "1 - Ajouter une plaque temporaire | 2 - Abandonner ");
            int option = Outils.saisirInt(0, 2);
            if (option == 1) {
                return ajouterPlaqueTemporaire(scanner);
            }
        }

        return null;
    }

    /**
     * Méthode utilisée pour construire l'objet RéservationDto à partir des informations récupérées lors de la procédure d'inscription.
     *
     * @param plaqueChoisie la plaque d'immatriculation du véhicule concerné par la recharge.
     * @param selectedBorneId l'id de la borne que el client pourra utiliser.
     * @return
     */
    private ReservationDto creerReservation(ImmatriculationDto plaqueChoisie, int selectedBorneId) {
        return new ReservationDto(
                plaqueChoisie.getIdPlaque(),
                selectedBorneId,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                plaqueChoisie.getTypeImmat(),
                0
        );
    }

    /**
     * Méthode utilisée pour afficher un récapitulatif de la réservation qui vient d'être faite.
     *
     * @param plaqueChoisie la plaque d'immatriculation du véhicule concerné par la recharge.
     * @param reservationDto la réservation effectuée.
     */
    private void afficherRecapReservation(ImmatriculationDto plaqueChoisie, ReservationDto reservationDto) {
        System.out.println("Résumé de la reservation : ");
        System.out.println("Votre véhicule : " + miseEnFormeImmat(plaqueChoisie.getPlaque()));
        System.out.println("Le numéro de votre borne : " + reservationDto.getIdBorne());
        System.out.println("L'heure de début de votre recharge : " + reservationDto.getHeureDebut().format(Outils.FORMAT_DATES_RESERVATIONS));
        System.out.println("L'heure de fin de votre recharge : " + reservationDto.getHeureFin().format(Outils.FORMAT_DATES_RESERVATIONS));
    }

    /**
     * Méthode utilisée pour récupérer la première borne disponible.
     *
     * @param bornesDispo la liste de bornes disponibles.
     * @return {int} l'id de la première borne disponible.
     */
    private int selectBorne(List<Integer> bornesDispo) {
        int selectedBorneId = bornesDispo.get(0);
        System.out.println("La borne numéro " + selectedBorneId + " est disponible pour ce créneau.\n");
        return selectedBorneId;
    }

    /**
     * Méthode utilisée pour ajouter une plaque d'immatriculation temporaire (pour les voitures de location par exemple).
     *
     * @param scanner le scanner utilisé pour récupérer les inputs utilisateur.
     * @return {ImmatriculationDto} la plaque d'immatriculation
     */
    private ImmatriculationDto ajouterPlaqueTemporaire(Scanner scanner) {
        System.out.println("Ajout de plaque d'immatriculation temporaire : ");
        String plaque = Outils.recupererPlaqueImmat(scanner);
        //TODO il faut voir comment ajouter ça en base, sachant qu'il faut lier la plaque au client une fois insérée. (ajouter fonction)
        return new ImmatriculationDto(plaque, ETypeImmat.TEMPORAIRE);
    }

    /**
     * Méthode utilisée pour afficher une liste de plaques d'immatriculation.
     *
     * @param listeImmat la liste de plaques d'immatriculation à afficher.
     * @return {String} la liste mise en forme.
     */
    private String afficherPlaquesImmatriculation(List<ImmatriculationDto> listeImmat) {
        StringBuilder sb = new StringBuilder();
        sb.append("Liste des immatriculations : \n");
        for(int i = 0; i < listeImmat.size(); i++) {
            sb.append(i+1);
            sb.append(" - ");
            sb.append(miseEnFormeImmat(listeImmat.get(i).getPlaque()));
            sb.append("\n");
        }
        return sb.toString();
    }
        //TODO : ces méthodes doivent disparaitre
     private List<Integer> mockListeBornesDispo() {
         return new ArrayList<>(List.of(1,2,3,4));
     }

     public List<ImmatriculationDto> mockListeImmatsClient() {
         return List.of(
                new ImmatriculationDto(44, "AA111BB", ETypeImmat.NORMALE),
                new ImmatriculationDto(62, "CC222DD", ETypeImmat.NORMALE)
        );
    }

}
